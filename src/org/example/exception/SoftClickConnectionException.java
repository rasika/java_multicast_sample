package org.example.exception;

public class SoftClickConnectionException extends Exception{

    public SoftClickConnectionException() {
        super();
    }

    public SoftClickConnectionException(String message) {
        super(message);
    }

    public SoftClickConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoftClickConnectionException(Throwable cause) {
        super(cause);
    }

    protected SoftClickConnectionException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
