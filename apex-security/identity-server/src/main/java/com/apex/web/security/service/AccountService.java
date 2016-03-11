package com.apex.web.security.service;

import com.apex.web.security.domain.Account;

public interface AccountService {

    Account saveOrUpdate(Account user);

    Account getByUsername(String username);

    Account getBySecurityTicket(String ticket);

}
