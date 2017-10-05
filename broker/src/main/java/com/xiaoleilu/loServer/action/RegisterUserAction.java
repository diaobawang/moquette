package com.xiaoleilu.loServer.action;

import com.hazelcast.core.HazelcastInstance;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;


@HttpMethod("POST")
@Route("/api/user/register")
public class RegisterUserAction implements Action{
    @Override
    public void doAction(Request request, Response response, HazelcastInstance hzInstance) {

    }
}
