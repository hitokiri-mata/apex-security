package com.apex.web.security.exception;

public class LogiServiceException extends Exception {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    public LogiServiceException(String message) {
	super(message);
    }

    public LogiServiceException(Throwable cause) {
	super(cause);
    }

    public LogiServiceException(String message, Throwable cause) {
	super(message, cause);
    }

}
