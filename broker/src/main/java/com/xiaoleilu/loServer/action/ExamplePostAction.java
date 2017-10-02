package com.xiaoleilu.loServer.action;

import com.hazelcast.core.HazelcastInstance;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import io.netty.handler.codec.http.HttpResponseStatus;

@Route("/api/example")
@HttpMethod("POST")
public class ExamplePostAction implements Action {

    @Override
    public void doAction(Request request, Response response, HazelcastInstance hzInstance) {
        response.setStatus(HttpResponseStatus.OK);
        response.setContent("Welcome post example");
    }
}
