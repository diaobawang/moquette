package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.OutputGetUser;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.proto.UserOuterClass;

@Route("/api/group")
@HttpMethod("GET")
public class GetGroupInfoAction extends Action {

    @Override
    public void action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            RestResult result = null;
            String groupId = request.getParam("groupId");
            if (groupId == null || groupId.isEmpty()) {
                result = RestResult.resultOf(RestResult.ErrorCode.Invalid_Parameter);
            } else {
                UserOuterClass.User user = messagesStore.getUserInfo(groupId);
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
