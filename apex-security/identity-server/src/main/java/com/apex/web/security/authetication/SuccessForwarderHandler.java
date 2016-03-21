package com.apex.web.security.authetication;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.substringAfter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple component encharge to redirect the system to source request after the
 * login process are succesful.
 * <p>
 * <i>View a Source Code</i>&nbsp;{@link SuccessForwarderHandler}
 * </p>
 * 
 * @author hitokiri
 *
 */
@Slf4j
@Setter
@Component
public class SuccessForwarderHandler implements AuthenticationSuccessHandler {
    private static final String REDIRECT = "redirect=";
    private String home;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
	    HttpServletResponse response, Authentication authentication)
		    throws IOException, ServletException {
	redirectStrategy.sendRedirect(request, response,
		determineTargetUrl(request, response));
    }

    /**
     * 
     * @param request
     * @return
     */
    private String determineTargetUrl(HttpServletRequest request,
	    HttpServletResponse response) {
	HttpSession session = request.getSession();
	// cheking if user have a httpSession active into the system.
	if (session == null) {
	    throw new IllegalStateException(
		    "Not a user session initialized found.");
	}
	// retriven the original URL saved for spring.
	SavedRequest savedRequest = new HttpSessionRequestCache()
		.getRequest(request, response);
	if (savedRequest == null) {
	    return EMPTY;
	}
	// getting the redirect URL sending into authentication request.
	String targetURL = savedRequest.getRedirectUrl();
	if (response.isCommitted()) {
	    log.debug("Response has already been "
		    + "committed. Unable to redirect to " + targetURL);
	    return this.home;
	}
	log.debug("taget URL : " + targetURL);
	if (EMPTY.equals(targetURL) || targetURL == null) {
	    return this.home;
	}
	return substringAfter(targetURL, REDIRECT);
    }

}
