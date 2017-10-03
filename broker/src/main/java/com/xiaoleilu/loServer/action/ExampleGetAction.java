package com.xiaoleilu.loServer.action;

import com.hazelcast.core.HazelcastInstance;
import com.xiaoleilu.loServer.action.Action;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import io.moquette.spi.IMessagesStore;
import io.netty.handler.codec.http.HttpResponseStatus;

@Route("/api/example")
@HttpMethod("GET")
public class ExampleGetAction extends Action {
    @Override
    public void action(Request request, Response response) {
        response.setStatus(HttpResponseStatus.OK);
        response.setContent("Welcome get example");
    }
}
