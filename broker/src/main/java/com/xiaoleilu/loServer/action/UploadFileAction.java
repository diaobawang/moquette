package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.StaticLog;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.RequireAuthentication;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.*;
import com.xiaoleilu.loServer.pojos.InputCreateUser;
import io.moquette.server.config.QiniuConfig;
import io.moquette.spi.impl.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import win.liyufan.im.proto.UserOuterClass;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.UUID;

import static com.xiaoleilu.loServer.handler.HttpResponseHelper.getFileExt;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Names.ACCESS_CONTROL_ALLOW_HEADERS;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Route("/fs")
@HttpMethod("POST")
@RequireAuthentication
public class UploadFileAction extends Action {

    private static final Logger logger = LoggerFactory.getLogger(UploadFileAction.class);
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(false);

    @Override
    public void action(Request r, Response response) {
        if (r.getNettyRequest() instanceof FullHttpRequest) {

            FullHttpRequest request = (FullHttpRequest) r.getNettyRequest();
            String requestId = UUID.randomUUID().toString().replace("-", "");
            logger.info("HttpFileServerHandler received a request: method=" + request.getMethod() + ", uri=" + request.getUri() + ", requestId=" + requestId);

            if (!request.getDecoderResult().isSuccess()) {
                logger.warn("http decode failed!");
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                response.setContent("http decode failed");
                return;
            }

            multipartUpload(request, requestId, response);

        }
    }


    /**
     * multipart上传
     */
    private void multipartUpload(FullHttpRequest request, String requestId, Response response) {
        HttpPostRequestDecoder decoder = null;
        try {
            decoder = new HttpPostRequestDecoder(factory, request);
        } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
            logger.error("Failed to decode file data!", e1);
            response.setStatus(HttpResponseStatus.BAD_REQUEST);
            response.setContent("Failed to decode file data!");
            return;
        }


        if (decoder != null) {
            if (request instanceof HttpContent) {
                HttpContent chunk = (HttpContent) request;
                try {
                    decoder.offer(chunk);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
                    logger.warn("BAD_REQUEST, Failed to decode file data");
                    response.setStatus(HttpResponseStatus.BAD_REQUEST);
                    response.setContent("Failed to decode file data!");
                    return;
                }

                long fileTotalSize = 0;
                if (request.headers().contains("X-File-Total-Size")) {
                    try {
                        fileTotalSize = Integer.parseInt(request.headers().get("X-File-Total-Size"));
                    } catch (Exception e) {
                        logger.warn("invalid X-File-Total-Size value!");
                    }
                }

                readHttpDataChunkByChunk(response, decoder, requestId, HttpHeaders.isKeepAlive(request));

                if (chunk instanceof LastHttpContent) {

                }
            } else {
                logger.warn("BAD_REQUEST, Not a http request");
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                response.setContent("Not a http request");
            }
        }
    }

    /**
     * readHttpDataChunkByChunk
     */
    private void readHttpDataChunkByChunk(Response response, HttpPostRequestDecoder decoder, String requestId, boolean isKeepAlive) {
        try {
            while (decoder.hasNext()) {
                InterfaceHttpData data = decoder.next();
                if (data != null) {
                    try {
                        writeFileUploadData(data, response, requestId, isKeepAlive);
                    } finally {
                        data.release();
                    }
                }
            }
        } catch (Exception e) {
            logger.info("chunk end");
        }
    }

    /**
     * writeFileUploadData
     */
    private void writeFileUploadData(InterfaceHttpData data, Response response, String requestId, boolean isKeepAlive) {
        try {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                FileUpload fileUpload = (FileUpload) data;

                String remoteFileName = fileUpload.getFilename();
                long remoteFileSize = fileUpload.length();
                if (StringUtils.isNullOrEmpty(remoteFileName)) {
                    logger.warn("remoteFileName is empty!");

                    response.setStatus(HttpResponseStatus.BAD_REQUEST);
                    response.setContent("file name is empty");
                    return;
                }

                if (StringUtils.isNullOrEmpty(requestId)) {
                    logger.warn("requestId is empty!");
                    response.setStatus(HttpResponseStatus.BAD_REQUEST);
                    response.setContent("requestId is empty!");
                    return;
                }

                if (remoteFileSize > 10 * 1024 * 1024) {
                    logger.warn("file over limite!(" + remoteFileSize + ")");
                    response.setStatus(HttpResponseStatus.BAD_REQUEST);
                    response.setContent("file over limite!");
                    return;
                }



                String remoteFileExt = "";
                if (remoteFileName.lastIndexOf(".") == -1) {
                    remoteFileExt = "octetstream";
                    remoteFileName = remoteFileName + "." + remoteFileExt;

                } else {
                    remoteFileExt = getFileExt(remoteFileName);
                }

                if (StringUtils.isNullOrEmpty(remoteFileExt) || remoteFileExt.equals("ing")) {
                    logger.warn("Invalid file extention name");
                    response.setStatus(HttpResponseStatus.BAD_REQUEST);
                    response.setContent("Invalid file extention name");
                    return;
                }

                int remoteFileTotalSize = (int) remoteFileSize;


                ByteBuf byteBuf = null;
                int savedThunkSize = 0; // 分片接收保存的大小
                int offset = 0; // 断点续传开始位置

                String filePath = "./" + QiniuConfig.FILE_STROAGE_ROOT + "/" + requestId;
                logger.info("the file path is " + filePath);

                File tmpFile = new File("./" + QiniuConfig.FILE_STROAGE_ROOT + "/" + requestId);

                logger.info("before write the file");
                boolean isError = false;
                while (true) {
                    byte[] thunkData;
                    try {
                        byteBuf = fileUpload.getChunk(128 * 1024);
                        int readableBytesSize = byteBuf.readableBytes();
                        thunkData = new byte[readableBytesSize];
                        byteBuf.readBytes(thunkData);

                        put(tmpFile, offset, thunkData);

                        savedThunkSize += readableBytesSize;
                        offset += readableBytesSize;

                        if (savedThunkSize >= remoteFileSize) {
                            byteBuf.release();
                            fileUpload.release();

                            response.setStatus(HttpResponseStatus.OK);
                            response.setContent("{\"key\":\"" + requestId + "\"}");
                            break;
                        }
                    } catch (Exception e) {
                        logger.error("save thunckData error!", e);
                        if (fileUpload != null)
                            fileUpload.release();

                        if (byteBuf != null)
                            byteBuf.release();

                        response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                        response.setContent("服务器错误：" + e.getMessage());
                        isError = true;

                        return;
                    } finally {
                        thunkData = null;
                        if (isError) {
                            tmpFile.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("writeHttpData error!", e);
            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            response.setContent("服务器错误：" + e.getMessage());
        }
    }

    public static void put(File file, long pos, byte[] data) throws Exception {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(pos);
            raf.write(data);
        } finally {
            try {
                if (raf != null)
                    raf.close();
            } catch (Exception e) {
                logger.warn("release error!", e);
            }
        }
    }
}
