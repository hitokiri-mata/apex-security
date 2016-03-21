package com.apex.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author hitokiri
 *
 */
public class SecurityFilter implements Filter {
    public static final String CROSS_SITE_REQUEST_FORGERY_TOKEN = "XSRF-TOKEN";
    public static final String SECURITY_SERVER_URL = "secutityServerLoginUrl";
    public static final String SECURITY_SERVER_NAME = "securityServerName";
    private String securityServerLoginUrl;
    private String securityServerName;
    private FilterConfig config;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
	this.config = config;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
	    ServletResponse servletResponse, FilterChain filterChain)
		    throws IOException, ServletException {
	// http://www.sipeliculas.com/la-pianista
	HttpServletRequest request = (HttpServletRequest) servletRequest;
	HttpServletResponse response = (HttpServletResponse) servletResponse;
	this.securityServerLoginUrl = config
		.getInitParameter(SECURITY_SERVER_URL);
	this.securityServerName = config.getInitParameter(securityServerName);
	// getting the current security ticket from httpRequest and validate
	if (!validateTicket(getSecurityTicket(request))) {
	    response.sendRedirect(securityServerLoginUrl + "?redirect="
		    + request.getRequestURL());
	}
    }

    /**
     * 
     * @param request
     * @return
     */
    private String getSecurityTicket(HttpServletRequest request) {
	String securityTicket = null;
	Cookie[] cookies = request.getCookies();
	if (cookies == null) {
	    return null;
	}
	//
	for (Cookie cookie : cookies) {
	    if (CROSS_SITE_REQUEST_FORGERY_TOKEN.equals(cookie.getName())) {
		securityTicket = cookie.getValue();
		break;
	    }
	}
	return securityTicket;
    }

    private boolean validateTicket(String ticket) {
	return true;
    }
}
