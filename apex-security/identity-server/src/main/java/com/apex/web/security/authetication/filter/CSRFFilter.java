package com.apex.web.security.authetication.filter;

import static com.apex.web.security.Constants.WEB_APPLICATION_ROOT_PATH;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.apex.web.security.domain.Account;
import com.apex.web.security.domain.Session;
import com.apex.web.security.service.AccountService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple component encharge to create a CSRFToken in ordet to aviable tha
 * thriparty authentcation.
 * <p>
 * <i>View a Source Code</i>&nbsp;{@link CSRFFilter}
 * </p>
 * 
 * 
 * @author <a href="cesar.mata@yuxipacific.com">Cesar A Mata de Avila</a>
 * @version %I%,%G%
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class CSRFFilter extends OncePerRequestFilter {

    public static final String CROSS_SITE_REQUEST_FORGERY_TOKEN = "XSRF-TOKEN";
    // containt the accout service instance.
    private final @NonNull AccountService accountService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
	    HttpServletResponse response, FilterChain filterChain)
		    throws ServletException, IOException {
	Account account = null;
	CsrfToken csrfToken = (CsrfToken) request
		.getAttribute(CsrfToken.class.getName());
	if (csrfToken != null) {
	    Cookie cookie = WebUtils.getCookie(request,
		    CROSS_SITE_REQUEST_FORGERY_TOKEN);
	    // getting the current authentication
	    Authentication authentication = SecurityContextHolder.getContext()
		    .getAuthentication();
	    // getting a csrf security token.
	    String token = csrfToken.getToken();
	    // validate if the current user is authenticated into the
	    // application
	    if (authentication != null && authentication.isAuthenticated()) {
		account = accountService
			.getByPrincipal(authentication.getName());
	    }
	    // creating a new durable session informaction for the current user.
	    if (account != null
		    && !isSessionAssociateWithAccount(account, token)) {
		// create a new durable user session.
		Session session = new Session();
		session.setTicket(token);
		session.setLastRequest(new Date());
		session.setRemoteIPAddress(request.getRemoteAddr());
		session.setStartTime(new Date());
		session.setAccount(account);
		// getting the current http session ID.
		session.setJSessionId(request.getSession(true).getId());
		account.getSessions().add(session);
		accountService.saveOrUpdate(account);
		log.debug("creating a security ticket '" + token
			+ "' for account '"
			+ account.getCredential().getUsername() + "'");
	    }
	    // create the browser cokie with crossite ticket
	    if (cookie == null
		    || token != null && !token.equals(cookie.getValue())) {
		cookie = new Cookie(CROSS_SITE_REQUEST_FORGERY_TOKEN, token);
		cookie.setPath(WEB_APPLICATION_ROOT_PATH);
		response.addCookie(cookie);
	    }
	}
	filterChain.doFilter(request, response);
    }

    /**
     * Simple utility method encharge to validate if the current user account
     * have a Session associate with it
     * 
     * @param account
     *            user account
     * @param ticket
     *            current security ticket.
     * @return
     */
    private boolean isSessionAssociateWithAccount(Account account,
	    String securityTicket) {
	for (Session session : account.getSessions()) {
	    if (StringUtils.equals(session.getTicket(), securityTicket)) {
		return true;
	    }
	}
	return false;
    }
}