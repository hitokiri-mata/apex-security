package com.apex.web.security;

/**
 * 
 * @author hitokiri
 *
 */
public interface SecurityTicketValidator {
    /**
     * 
     * @param securityTicket
     */
    void validate(String securityTicket);
}
