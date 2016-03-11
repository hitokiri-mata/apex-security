package com.apex.web.security.authetication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author hitokiri
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class JPAAuthenticationProvider implements AuthenticationProvider {
    // containt the current user detail services provider for the application.
    private @NonNull UserDetailsService userDetailService;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication)
	    throws AuthenticationException {
	log.debug("Begin the authentication process for the user '"
		+ authentication.getName() + "'");
	if (authentication.isAuthenticated()) {
	    return authentication;
	}
	UserDetails userDetails = userDetailService
		.loadUserByUsername(authentication.getName());
	// validating the password stored in the database and the provider for
	// the user into authetication mechanism
	if (!StringUtils.equals(authentication.getCredentials().toString(),
		userDetails.getPassword())) {
	    throw new BadCredentialsException(
		    "The authentication porcess failed");
	}
	// return the application user credential with unique session token
	// asignend for the user.
	authentication = new UsernamePasswordAuthenticationToken(
		authentication.getPrincipal(), authentication.getCredentials(),
		userDetails.getAuthorities());
	return authentication;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
	return (UsernamePasswordAuthenticationToken.class
		.isAssignableFrom(authentication));
    }

}
