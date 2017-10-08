package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.OutputGetUserInfo;
import com.xiaoleilu.loServer.pojos.OutputSearhResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.proto.UserOuterClass;

import java.util.List;

@Route("/api/search")
@HttpMethod("GET")
public class SearchUserAction extends Action {

    @Override
    public void action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            RestResult result = null;
            String keyword = request.getParam("keyword");
            boolean fuzzy = false;
            int page = 0;
            try {
                fuzzy = Boolean.parseBoolean(request.getParam("fuzzy"));
            } catch (Exception e) {

            }

            try {
                page = Integer.parseInt(request.getParam("page"));
            } catch (Exception e) {

            }

            if (keyword == null || keyword.isEmpty()) {
                result = RestResult.resultOf(RestResult.ErrorCode.Invalid_Parameter);
            } else {
                List<UserOuterClass.User> users = messagesStore.searchUser(keyword, fuzzy, page);
                if (users == null || users.isEmpty()) {
                    result = RestResult.resultOf(RestResult.ErrorCode.User_Not_Exist);
                } else {
                    result = RestResult.ok(new OutputSearhResult(keyword, page, users));
                }

            }
            response.setStatus(HttpResponseStatus.OK);
            response.setContent(new Gson().toJson(result));
        }
    }
}
