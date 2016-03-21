package com.apex.web.security.service;

/**
 * 
 * @author hitokiri
 *
 */
public interface LogiService {
    /**
     * 
     * @param request
     * @return
     * @throws ServiceException
     */
    String getSecurityKey(String request) throws ServiceException;

}
