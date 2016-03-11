package com.apex.web.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apex.web.security.domain.Account;
import com.apex.web.security.domain.repository.AccountRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author <a href="cesar.mata@yuxipacfic.com">Cesar a Mata de Avila</a>
 * @Version %I%.%G%
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class JpaUserDetailService implements UserDetailsService {

    private @NonNull AccountRepository accountRepository;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username)
	    throws UsernameNotFoundException {
	log.debug("loading the user details for account '" + username + "'");
	Account account = accountRepository.findByCredentialUsername(username);
	if (account == null) {
	    throw new UsernameNotFoundException(
		    "user name '" + username + "' is not found'");
	}
	return new User(username, account.getCredential().getPassword(),
		account.isActive(), true, true, true, account.getRoles());
    }

}
