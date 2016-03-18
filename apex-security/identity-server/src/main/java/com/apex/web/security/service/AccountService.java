package com.apex.web.security.service;

import com.apex.web.security.domain.Account;

/**
 * Current <b>Account</b> Entity Service Interfaz used to defined Method need
 * implement for conrete service Class
 * <p>
 * <i>View a Source Code</i>&nbsp{@linkplain AccountService}
 * </p>
 * 
 * @author <a herf="mailto:cesar.mata@yuxipacific.com">Cesar a Mata de Avila</a>
 * @version %I%,%G%
 *
 */
public interface AccountService {
    /**
     * Simple method encharge to save or udate the current <b><i>Account</i></b>
     * entity values
     * 
     * @param user
     * @return
     */
    Account saveOrUpdate(Account account);

    /**
     * 
     * @param principal
     * @return
     */
    Account getByPrincipal(String principal);

    /**
     * 
     * @param ticket
     * @return
     */
    Account getBySecurityTicket(String ticket);

}
