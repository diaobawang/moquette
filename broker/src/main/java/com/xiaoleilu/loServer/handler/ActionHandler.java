package com.xiaoleilu.loServer.handler;

import java.io.IOException;

import com.hazelcast.core.HazelcastInstance;
import com.xiaoleilu.hutool.lang.Singleton;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.StaticLog;
import com.xiaoleilu.loServer.ServerSetting;
import com.xiaoleilu.loServer.action.Action;
import com.xiaoleilu.loServer.action.UnknownErrorAction;
import com.xiaoleilu.loServer.action.FileAction;
import com.xiaoleilu.loServer.filter.Filter;

import io.moquette.spi.IMessagesStore;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action处理单元
 * 
 * @author Looly
 */
public class ActionHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger Logger = LoggerFactory.getLogger(ActionHandler.class);

    public ActionHandler(IMessagesStore messagesStore) {
        Action.messagesStore = messagesStore;
    }

    @Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        Logger.info("Http request whit url {}", fullHttpRequest.uri());
		final Request request = Request.build(ctx, fullHttpRequest);
		final Response response = Response.build(ctx, request);
		response.setContentType("application/json");
		try {
			//do filter
			boolean isPass = this.doFilter(request, response);
			
			if(isPass){
				//do action
				this.doAction(request, response);
			}
		} catch (Exception e) {
			Action errorAction = ServerSetting.getErrorAction(ServerSetting.MAPPING_ERROR);
			request.putParam(UnknownErrorAction.ERROR_PARAM_NAME, e);
			response.setContent(e.toString());
			errorAction.doAction(request, response);
		}
		
		//如果发送请求未被触发，则触发之，否则跳过。
		if(false ==response.isSent()){
			response.send();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause instanceof IOException){
			Logger.warn("{}", cause.getMessage());
		}else{
			super.exceptionCaught(ctx, cause);
		}
	}
	
	//---------------------------------------------------------------------------------------- Private method start
	/**
	 * 执行过滤
	 * @param request 请求
	 * @param response 响应
	 * @@return  是否继续
	 */
	private boolean doFilter(Request request, Response response) {
		//全局过滤器
		Filter filter = ServerSetting.getFilter(ServerSetting.MAPPING_ALL);
		if(null != filter){
			if(false == filter.doFilter(request, response)){
				return false;
			}
		}
		
		//自定义Path过滤器
		filter = ServerSetting.getFilter(request.getPath());
		if(null != filter){
			if(false == filter.doFilter(request, response)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 执行Action
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	private void doAction(Request request, Response response){
		Action action = ServerSetting.getAction(request.getPath(), request.getMethod().toUpperCase());
		if (request.getPath().startsWith("/api") && action == null) {
		    action = ServerSetting.getErrorAction(ServerSetting.MAPPING_ERROR);
        }
		if (null == action) {
			//查找匹配所有路径的Action
			action = ServerSetting.getAction(ServerSetting.MAPPING_ALL, request.getMethod());
			if(null == action){
				// 非Action方法，调用静态文件读取
				action = Singleton.get(FileAction.class);
			}
		}

		action.doAction(request, response);
	}
	//---------------------------------------------------------------------------------------- Private method start
}
