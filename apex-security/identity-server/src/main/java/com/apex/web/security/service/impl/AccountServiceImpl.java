package com.apex.web.security.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apex.web.security.domain.Account;
import com.apex.web.security.domain.repository.AccountRepository;
import com.apex.web.security.service.AccountService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class AccountServiceImpl implements AccountService {

    private final @NonNull AccountRepository accountRepository;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.security.service.UserService#create(com.apex.security.domain.
     * entity.User)
     */
    @Override
    public Account saveOrUpdate(Account account) {
	return accountRepository.save(account);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.AccountService#findByUsername(java.lang.
     * String)
     */
    @Override
    public Account getByPrincipal(String username) {
	return accountRepository.findByCredentialUsername(username);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.AccountService#getBySecurityTicket(java.
     * lang.String)
     */
    @Override
    public Account getBySecurityTicket(String ticket) {
	return accountRepository.findBySessionsTicket(ticket);
    }

}
