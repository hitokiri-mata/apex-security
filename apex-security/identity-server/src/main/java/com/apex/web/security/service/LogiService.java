package com.apex.web.security.service;

import com.apex.web.security.exception.LogiServiceException;

public interface LogiService {

    String getSecurityKey(String request) throws LogiServiceException;

}
