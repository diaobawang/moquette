package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.OutputGetGroupInfo;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.proto.GroupOuterClass;

@Route("/api/group")
@HttpMethod("GET")
public class GetGroupInfoAction extends Action {

    @Override
    public void action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            RestResult result = null;
            String groupId = request.getParam("groupId");
            int line = request.getParam("line") == null ? 0 : Integer.parseInt(request.getParam("line"));
            if (groupId == null || groupId.isEmpty()) {
                result = RestResult.resultOf(RestResult.ErrorCode.Invalid_Parameter);
            } else {
                GroupOuterClass.GroupInfo groupInfo = messagesStore.getGroupInfo(groupId, line);
                if (groupInfo == null) {
                    result = RestResult.resultOf(RestResult.ErrorCode.User_Not_Exist);
                } else {
                    result = RestResult.ok(OutputGetGroupInfo.fromGroupInfo(groupInfo));
                }
            }
            response.setStatus(HttpResponseStatus.OK);
            response.setContent(new Gson().toJson(result));
        }
    }
}
