package com.apex.web.security.authetication;

import static com.apex.web.security.Constants.COMMA;
import static com.apex.web.security.Constants.LOGI_SECRETE_KEY;
import static com.apex.web.security.Constants.LOGI_SERVER_IP_ADDRESS;
import static com.apex.web.security.Constants.LOGI_USER_NAME;
import static com.apex.web.security.Constants.LOGI_USER_ROLES;
import static com.apex.web.security.Constants.USER_REMOTE_IP_ADDRESS;
import static com.apex.web.security.Constants.WEB_APPLICATION_ROOT_PATH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.apex.web.security.domain.Account;
import com.apex.web.security.exception.LogiServiceException;
import com.apex.web.security.service.AccountService;
import com.apex.web.security.service.LogiService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class LogiAnalyticSecurityKeyHandler
	extends SavedRequestAwareAuthenticationSuccessHandler {

    private String logiSecurityKeyRequest = "http://" + LOGI_SERVER_IP_ADDRESS
	    + "/myLogiApp/rdTemplate/rdGetSecureKey.aspx?Username="
	    + LOGI_USER_NAME + "&ClientBrowserAddress=" + USER_REMOTE_IP_ADDRESS
	    + "&Roles=" + LOGI_USER_ROLES;

    private @NonNull AccountService accountService;
    private @NonNull LogiService logiService;

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
	super.setUseReferer(true);
	super.onAuthenticationSuccess(request, response, authentication);
	Account account = null;
	// getting a logi secrete key cookie for current request
	Cookie cookie = WebUtils.getCookie(request, LOGI_SECRETE_KEY);
	// validate if user is authenticate
	if (authentication != null && authentication.isAuthenticated()) {
	    account = accountService.getByUsername(authentication.getName());
	}
	// extracting the current roles and right
	List<String> roles = new ArrayList<>();
	// getting all roles for the current authenticate user.
	account.getRoles().forEach(role -> roles.add(role.getName()));
	System.out
		.println("..... ... ...." + logiSecurityKeyRequest
			.replace(LOGI_USER_NAME, authentication.getName())
			.replace(USER_REMOTE_IP_ADDRESS,
				request.getRemoteAddr())
		.replace(LOGI_USER_ROLES, StringUtils.join(roles, COMMA)));
	//
	String url = "http://www.google.com/search?q=httpClient";
	try {
	    // getting a secret security key from logi server
	    String securityKey = logiService.getSecurityKey(url);
	    if (cookie == null && StringUtils.isNoneEmpty(securityKey)) {
		cookie = new Cookie(LOGI_SECRETE_KEY, securityKey);
		cookie.setPath(WEB_APPLICATION_ROOT_PATH);
		response.addCookie(cookie);
	    }
	} catch (LogiServiceException e) {
	    log.error("Opp!, Sorry some king the error occurred"
		    + " trying to get a logic security key.", e);
	    throw new ServletException("", e);
	}
    }

}
