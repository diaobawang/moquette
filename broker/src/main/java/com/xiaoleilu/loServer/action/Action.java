package com.xiaoleilu.loServer.action;

import com.xiaoleilu.loServer.annotation.RequireAuthentication;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import io.moquette.spi.IMessagesStore;
import win.liyufan.im.DBUtil;

/**
 * 请求处理接口<br>
 * 当用户请求某个Path，则调用相应Action的doAction方法
 * @author Looly
 *
 */

abstract public class Action {
    public static IMessagesStore messagesStore = null;

    public boolean preAction(Request request, Response response) {
        if (getClass().getAnnotation(RequireAuthentication.class) != null) {
            //do authentication
        }
        return true;
    }
	public void doAction(Request request, Response response) {
        if (preAction(request, response)) {
            if (isTransactionAction()) {
                DBUtil.beginTransaction();
                try {
                    action(request, response);
                    DBUtil.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    DBUtil.roolback();
                    throw e;
                }
            }
            afterAction(request, response);
        } else {

        }
    }
    public boolean isTransactionAction() {
        return false;
    }
    abstract public void action(Request request, Response response);
    public void afterAction(Request request, Response response) {

    }
}
