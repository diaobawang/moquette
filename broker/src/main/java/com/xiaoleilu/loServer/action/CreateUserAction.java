package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.InputCreateUser;
import io.moquette.spi.IMessagesStore;
import io.moquette.spi.impl.Utils;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.proto.UserOuterClass;

@Route("/api/user")
@HttpMethod("PUT")
public class CreateUserAction extends Action {

    @Override
    public boolean isTransactionAction() {
        return true;
    }

    @Override
    public void action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest)request.getNettyRequest();
            byte[] bytes = Utils.readBytesAndRewind(fullHttpRequest.content());
            String content = new String(bytes);
            Gson gson = new Gson();
            InputCreateUser inputCreateUser = gson.fromJson(content, InputCreateUser.class);
            if (inputCreateUser != null && inputCreateUser.getUserId() != null && inputCreateUser.getUserId().length() > 0) {
                if (inputCreateUser.getPortrait() == null || inputCreateUser.getPortrait().length() == 0) {
                    inputCreateUser.setPortrait("https://avatars.io/gravatar/" + inputCreateUser.getUserId());
                }

                UserOuterClass.User.Builder newUserBuilder = UserOuterClass.User.newBuilder()
                    .setUid(inputCreateUser.getUserId());
                if (inputCreateUser.getName() != null)
                    newUserBuilder.setName(inputCreateUser.getName());
                if (inputCreateUser.getDisplayName() != null)
                    newUserBuilder.setDisplayName(inputCreateUser.getDisplayName());
                if (inputCreateUser.getPortrait() != null)
                    newUserBuilder.setPortrait(inputCreateUser.getPortrait());
                if (inputCreateUser.getEmail() != null)
                    newUserBuilder.setEmail(inputCreateUser.getEmail());
                if (inputCreateUser.getAddress() != null)
                    newUserBuilder.setAddress(inputCreateUser.getAddress());
                if (inputCreateUser.getCompany() != null)
                    newUserBuilder.setCompany(inputCreateUser.getCompany());
                if (inputCreateUser.getMobile() != null)
                    newUserBuilder.setMobile(inputCreateUser.getMobile());
                if (inputCreateUser.getExtra() != null)
                    newUserBuilder.setExtra(inputCreateUser.getExtra());

                newUserBuilder.setUpdateDt(System.currentTimeMillis());

                messagesStore.addUserInfo(newUserBuilder.build());
            }
            response.setStatus(HttpResponseStatus.OK);
            RestResult result = RestResult.ok(inputCreateUser.getUserId());
            response.setContent(new Gson().toJson(result));
        }
    }
}
