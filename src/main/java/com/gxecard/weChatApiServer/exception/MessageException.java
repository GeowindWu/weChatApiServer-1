package com.gxecard.weChatApiServer.exception;

/**
 * 自定义异常
 */
public class MessageException extends RuntimeException {
	private static final long serialVersionUID = 28739729710379L;
	private final String errorCode;

    public MessageException(String errorDescription) {
        super(errorDescription);
        this.errorCode = "99999";
    }

    public MessageException(String errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
    }

    public MessageException(String errorCode, String errorDescription, Throwable cause) {
        super(errorDescription, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return getMessage();
    }
}
