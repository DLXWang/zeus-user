package com.xinxu.user.exception;

public class ExpireException extends RuntimeException{

    public ExpireException() {
        super();
    }

    public ExpireException(String message) {
        super(message);
    }

    public ExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpireException(Throwable cause) {
        super(cause);
    }
}
