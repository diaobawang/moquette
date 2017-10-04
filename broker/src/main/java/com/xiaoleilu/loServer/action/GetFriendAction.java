package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.RequireAuthentication;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.InputCreateUser;
import io.moquette.spi.impl.Utils;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.proto.UserOuterClass;

import java.util.ArrayList;
import java.util.List;

@Route("/api/friend")
@HttpMethod("GET")
@RequireAuthentication
public class GetFriendAction extends Action {

    @Override
    public boolean isTransactionAction() {
        return false;
    }

    @Override
    public void action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest)request.getNettyRequest();

            String userId = request.getParam("userId");
            List<String> friendList = messagesStore.getFriendList(userId);

            response.setStatus(HttpResponseStatus.OK);
            response.setContent(new Gson().toJson(friendList));
        }
    }
}
