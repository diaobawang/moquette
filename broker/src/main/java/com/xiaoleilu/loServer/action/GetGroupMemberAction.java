package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.OutputGetGroupInfo;
import com.xiaoleilu.loServer.pojos.OutputGroupMember;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.proto.GroupOuterClass;

import java.util.List;

@Route("/api/group_member")
@HttpMethod("GET")
public class GetGroupMemberAction extends Action {

    @Override
    public void action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            RestResult result = null;
            String groupId = request.getParam("groupId");
            if (groupId == null || groupId.isEmpty()) {
                result = RestResult.resultOf(RestResult.ErrorCode.Invalid_Parameter);
            } else {
                GroupOuterClass.GroupInfo groupInfo = messagesStore.getGroupInfo(groupId);
                if (groupInfo == null) {
                    result = RestResult.resultOf(RestResult.ErrorCode.User_Not_Exist);
                } else {
                    List<GroupOuterClass.GroupMember> members = messagesStore.getGroupMembers(groupId);
                    result = RestResult.ok(OutputGroupMember.memberListFrom(members));
                }
            }
            response.setStatus(HttpResponseStatus.OK);
            response.setContent(new Gson().toJson(result));
        }
    }
}
