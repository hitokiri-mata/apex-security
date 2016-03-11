package com.apex.web.security.authetication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import com.apex.web.security.domain.Session;
import com.apex.web.security.domain.Session.Agent;
import com.apex.web.security.service.SessionService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Current component encharge to update close session date information into de
 * database
 * 
 * <p>
 * <i>View a Source Code </i>&nbsp;{@link SuccessLogoutHandler}
 * </p>
 * 
 * @author <a href="mailto:cesar.mata@yuxipacific.com">Cesar a Mata de Avila</a>
 * @version %I%,%G%
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class SuccessLogoutHandler extends SimpleUrlLogoutSuccessHandler {
    // containt the current session service to performing all session operations
    private @NonNull SessionService sessionService;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.logout.
     * LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
	    HttpServletResponse response, Authentication authentication)
		    throws IOException, ServletException {
	log.info("performing..the logout operation"
		+ " for the current user session.");
	// getting the current user account information.
	Session session = sessionService.getByTicket(
		((CsrfToken) request.getAttribute(CsrfToken.class.getName()))
			.getToken());
	// validating the current account and updating the close session
	// information into application database.
	if (session != null) {
	    session.setEndTime(new Date());
	    session.setCloseBy(Agent.USER);
	    sessionService.saveOrUpdate(session);
	}
    }

}