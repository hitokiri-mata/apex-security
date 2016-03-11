package com.apex.web.security.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.apex.web.security.domain.Account;

/**
 * Simple system component used for handled the persistent information about the
 * system user stored in different data repositories the application system.
 * <p>
 * <i>View a source code</i>&nbsp;<b>{@code UerRepository}</b>
 * </p>
 * 
 * @author <a href="mailto:cesar.mata@yuxipacific.com">Cesar A Mata de Avila</a>
 * @version %I%,%G% $Date
 *
 */
public interface AccountRepository
	extends PagingAndSortingRepository<Account, Long> {
    /**
     * Simple method encharge to find a user account by username
     * 
     * @param username
     *            Current credential username.
     * @return
     */
    Account findByCredentialUsername(String username);

    /**
     * Simple method encharge to find a user account by security session ticket.
     * 
     * @param ticket
     * @return
     */
    Account findBySessionTicket(String ticket);

}
