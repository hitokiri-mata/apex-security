package com.apex.web.security.validation;

/**
 * 
 * @author hitokiri
 *
 */
public interface TicketValidator {
    /**
     * 
     * @param ticket
     * @throws ValidationException
     */
    void validate(String ticket) throws ValidationException;

}
