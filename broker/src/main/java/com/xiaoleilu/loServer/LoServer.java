package com.xiaoleilu.loServer;

import com.hazelcast.core.HazelcastInstance;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.StaticLog;
import com.xiaoleilu.hutool.util.DateUtil;
import com.xiaoleilu.loServer.action.Action;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.ActionHandler;

import io.moquette.spi.IMessagesStore;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import com.xiaoleilu.loServer.action.ClassUtil;

import java.io.IOException;

/**
 * LoServer starter<br>
 * 用于启动服务器的主对象<br>
 * 使用LoServer.start()启动服务器<br>
 * 服务的Action类和端口等设置在ServerSetting中设置
 * @author Looly
 *
 */
public class LoServer {
	private static final Log log = StaticLog.get();
	private int port;
    private IMessagesStore messagesStore;
    private Channel channel;

    public LoServer(int port, IMessagesStore messagesStore) {
        this.port = port;
        this.messagesStore = messagesStore;
    }

    /**
	 * 启动服务
	 * @throws InterruptedException 
	 */
	public void start() throws InterruptedException {
		long start = System.currentTimeMillis();
		
		// Configure the server.
		final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		final EventLoopGroup workerGroup = new NioEventLoopGroup();

        registerAllAction();

		try {
			final ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.channel(NioServerSocketChannel.class)
//				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
						.addLast(new HttpServerCodec())
						//把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
						.addLast(new HttpObjectAggregator(65536))
						//压缩Http消息
//						.addLast(new HttpChunkContentCompressor())
						//大文件支持
						.addLast(new ChunkedWriteHandler())
						
						.addLast(new ActionHandler(messagesStore));
					}
				});
			
			channel = b.bind(port).sync().channel();
			log.info("***** Welcome To LoServer on port [{}], startting spend {}ms *****", port, DateUtil.spendMs(start));
		} finally {

		}
	}
    public void shutdown() {
        if (this.channel != null) {
            this.channel.close();
            try {
                this.channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerAllAction() {
        try {
            for (Class cls:ClassUtil.getAllAssignedClass(Action.class)
                 ) {
                if(cls.getAnnotation(Route.class) != null) {
                    ServerSetting.setAction((Class<? extends Action>)cls);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
