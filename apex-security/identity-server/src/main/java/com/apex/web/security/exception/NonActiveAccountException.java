package com.apex.web.security.exception;

public class NonActiveAccountException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public NonActiveAccountException() {
	super();
    }

    public NonActiveAccountException(String message) {
	super(message);
    }

}
