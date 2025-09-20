package com.gogo.statistic_service.exception;

public class StatServiceException extends RuntimeException {

    public StatServiceException() {
        super();
    }

    public StatServiceException(String message) {
        super(message);
    }

    public StatServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatServiceException(Throwable cause) {
        super(cause);
    }
}

