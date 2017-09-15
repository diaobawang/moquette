package win.liyufan.im;

public enum ErrorCode {
    //General error
    ERROR_CODE_SUCCESS(1000, "success"),
    ERROR_CODE_NOT_AUTHENTICATED(1001, "not authenticated"),

    //Auth error
    ERROR_CODE_TOKEN_ERROR(1101, "token error"),
    ERROR_CODE_OUT_OF_SERVICE(1102, "out of service"),
    ERROR_CODE_USER_FORBIDDEN(1103, "user forbidden"),

    //Auth error
    ERROR_CODE_NOT_IN_GROUP(1201, "token error"),

    //Group error
    ERROR_CODE_GROUP_ALREADY_EXIST(1304, "group aleady exist"),
    ERROR_CODE_GROUP_NOT_EXIST(1304, "group not exist");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
