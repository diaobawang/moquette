package win.liyufan.im;

public enum ErrorCode {
    //General error
    ERROR_CODE_SUCCESS(0, "success"),
    ERROR_CODE_NOT_AUTHENTICATED(1, "not authenticated"),
    ERROR_CODE_INVALID_DATA(2, "invalid data"),

    ERROR_CODE_SERVER_ERROR(3, "server error"),

    //Auth error
    ERROR_CODE_TOKEN_ERROR(4, "token error"),
    ERROR_CODE_OUT_OF_SERVICE(5, "out of service"),
    ERROR_CODE_USER_FORBIDDEN(6, "user forbidden"),

    //Message error
    ERROR_CODE_NOT_IN_GROUP(7, "token error"),
    ERROR_CODE_INVALID_MESSAGE(8, "invalid message"),

    //Group error
    ERROR_CODE_GROUP_ALREADY_EXIST(9, "group aleady exist"),
    ERROR_CODE_GROUP_NOT_RIGHT(10, "no right to operate group"),
    ERROR_CODE_GROUP_NOT_EXIST(11, "group not exist");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
