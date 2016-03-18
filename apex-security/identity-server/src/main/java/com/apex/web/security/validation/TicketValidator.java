package com.apex.web.security.validation;

import com.apex.web.security.exception.ValidationException;

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
