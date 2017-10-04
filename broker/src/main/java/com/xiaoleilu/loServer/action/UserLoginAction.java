package com.xiaoleilu.loServer.action;

import com.google.gson.Gson;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import com.xiaoleilu.loServer.pojos.InputUserLogin;
import io.moquette.spi.impl.Utils;
import io.moquette.spi.impl.security.TokenAuthenticator;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import win.liyufan.im.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Route("/api/login")
@HttpMethod("POST")
public class UserLoginAction extends Action {

    @Override
    public void action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            RestResult result;
            FullHttpRequest fullHttpRequest = (FullHttpRequest)request.getNettyRequest();
            byte[] bytes = Utils.readBytesAndRewind(fullHttpRequest.content());
            String content = new String(bytes);
            Gson gson = new Gson();
            InputUserLogin inputUserLogin = gson.fromJson(content, InputUserLogin.class);
            if (inputUserLogin != null && inputUserLogin.getName() != null && inputUserLogin.getPassword() != null) {
                List<String> userIdRet = new ArrayList<>();
                ErrorCode errorCode = messagesStore.login(inputUserLogin.getName(), inputUserLogin.getPassword(), userIdRet);
                if (errorCode == ErrorCode.ERROR_CODE_SUCCESS) {
                    TokenAuthenticator authenticator = new TokenAuthenticator();

                    String strToken = authenticator.generateToken(userIdRet.get(0));
                    result = RestResult.ok(strToken);
                } else {
                    if (errorCode == ErrorCode.ERROR_CODE_USER_NOT_EXIST) {
                        result = RestResult.resultOf(RestResult.ErrorCode.User_Not_Exist);
                    } else /*if (errorCode == ErrorCode.ERROR_CODE_PASSWORD_INCORRECT)*/ {
                        result = RestResult.resultOf(RestResult.ErrorCode.Password_Incorrect);
                    }
                }
            } else {
                result = RestResult.resultOf(RestResult.ErrorCode.Invalid_Parameter);
            }
            response.setStatus(HttpResponseStatus.OK);

            response.setContent(new Gson().toJson(result));
        }
    }
}
