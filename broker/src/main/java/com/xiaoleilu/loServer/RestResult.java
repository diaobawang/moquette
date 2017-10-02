package com.xiaoleilu.loServer;

public class RestResult {
    public enum ErrorCode {
        OK(0, "success"),
        Invalid_Parameter(1, "Invalid parameter"),
        User_Not_Exist(2, "user not exist");

        int code;
        String msg;

        ErrorCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    int code;
    String msg;
    Object result;

    public static RestResult ok(Object object) {
        return resultOf(ErrorCode.OK, ErrorCode.OK.msg, object);
    }

    public static RestResult resultOf(ErrorCode errorCode) {
        return resultOf(errorCode, errorCode.msg, null);
    }

    public static RestResult resultOf(ErrorCode errorCode, String msg) {
        return resultOf(errorCode, msg, null);
    }

    public static RestResult resultOf(ErrorCode errorCode, String msg, Object object) {
        RestResult result = new RestResult();
        result.code = errorCode.code;
        result.msg = msg;
        result.result = object;
        return result;
    }

    public void setErrorCode(ErrorCode errorCode) {
        setCode(errorCode.code);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
