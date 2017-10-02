package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.InputCreateUser;
import com.xiaoleilu.loServer.pojos.OutputGetUser;
import io.moquette.spi.IMessagesStore;
import io.moquette.spi.impl.Utils;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.proto.UserOuterClass;

@Route("/api/user")
@HttpMethod("GET")
public class GetUserAction implements Action {

    @Override
    public void doAction(Request request, Response response, IMessagesStore messagesStore) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            RestResult result = null;
            String userId = request.getParam("userId");
            if (userId == null || userId.isEmpty()) {
                result = RestResult.resultOf(RestResult.ErrorCode.Invalid_Parameter);
            } else {
                UserOuterClass.User user = messagesStore.getUserInfo(userId);
                if (user == null) {
                    result = RestResult.resultOf(RestResult.ErrorCode.User_Not_Exist);
                } else {
                    result = RestResult.ok(OutputGetUser.fromUser(user));
                }

            }
            response.setStatus(HttpResponseStatus.OK);
            response.setContent(new Gson().toJson(result));
        }
    }
}
