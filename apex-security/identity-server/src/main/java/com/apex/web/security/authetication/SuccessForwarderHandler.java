package com.apex.web.security.authetication;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.trim;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
 * @author <a href="mailto:cesar.mata@yuxipacific.com">Cesar Mata de Avila</a>
 * @version %I%,%G%
 *
 */
@Slf4j
@Setter
@Component
public class SuccessForwarderHandler implements AuthenticationSuccessHandler {
    private static final String SPRING_SECURITY_SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";
    private static final String REDIRECT = "redirect=";
    // containt home page address
    private String home;
    // containt redirect strategy
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
	try {
	    String targetURL = new URIBuilder()
		    .setPath(determineTargetUrl(request, response))
		    .addParameter("ticket", "123456").build().toString();
	    System.out.println("//-//-//-//-// "+targetURL);
	    redirectStrategy.sendRedirect(request, response, targetURL);
	} catch (URISyntaxException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Simple method encharge to getting de tager URL for redirect.
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
	SavedRequest savedRequest = (SavedRequest) session
		.getAttribute(SPRING_SECURITY_SAVED_REQUEST);
	// validate if saved request is null
	if (savedRequest == null) {
	    return this.home;
	}
	// getting the redirect URL sending into authentication request.
	String targetURL = savedRequest.getRedirectUrl();
	if (response.isCommitted()) {
	    log.debug("Response has already been "
		    + "committed. Unable to redirect to " + targetURL);
	    return this.home;
	}
	log.debug("taget URL : " + targetURL);
	// getting the redirect from client application.
	String redirect = substringAfter(targetURL, REDIRECT);
	if (!EMPTY.equals(trim(redirect)) || redirect == null) {
	    return this.home;
	}
	return redirect;
    }

}
