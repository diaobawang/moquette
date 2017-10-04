package win.liyufan.im;

public enum ErrorCode {
    //General error
    ERROR_CODE_SUCCESS(0, "success"),
    ERROR_CODE_NOT_AUTHENTICATED(1, "not authenticated"),
    ERROR_CODE_INVALID_DATA(2, "invalid data"),
    ERROR_CODE_UNKNOWN_METHOD(3, "unknown method"),
    ERROR_CODE_SERVER_ERROR(4, "server error"),

    //Auth error
    ERROR_CODE_TOKEN_ERROR(5, "token error"),
    ERROR_CODE_OUT_OF_SERVICE(6, "out of service"),
    ERROR_CODE_USER_FORBIDDEN(7, "user forbidden"),

    //Message error
    ERROR_CODE_NOT_IN_GROUP(8, "not in group"),
    ERROR_CODE_INVALID_MESSAGE(9, "invalid message"),

    //Group error
    ERROR_CODE_GROUP_ALREADY_EXIST(10, "group aleady exist"),
    ERROR_CODE_GROUP_NOT_RIGHT(11, "no right to operate group"),
    ERROR_CODE_GROUP_NOT_EXIST(12, "group not exist"),

    //user error
    ERROR_CODE_USER_NOT_EXIST(13, "user not exist"),
    ERROR_CODE_PASSWORD_INCORRECT(14, "password incorrect"),;

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
