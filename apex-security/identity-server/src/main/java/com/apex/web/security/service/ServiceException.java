package com.apex.web.security.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param message
     */
    public ServiceException(String message) {
	super(message);
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
	super(message, cause);
    }
}
