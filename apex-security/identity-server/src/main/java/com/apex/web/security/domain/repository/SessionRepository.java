package com.apex.web.security.domain.repository;

import java.util.Date;
import java.util.List;
import static com.apex.web.security.Constants.SECURITY_TICKET_CACHE_NAME;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.apex.web.security.domain.Session;

/**
 * Simple repository encharge to handling the persistent information about the
 * system user sessions.
 * 
 * <p>
 * <i>View a Source Code</i>&nbsp;{@link SessionRepository}
 * </p>
 * 
 * @author <a href="mailto:cesar.mata@yuxipacific.com">Cesar a Mata de Avila</a>
 * @verison %I%,%G%
 *
 */

public interface SessionRepository
	extends PagingAndSortingRepository<Session, Long> {
    /**
     * Simple method encharge to find every session with last request past over
     * current time.
     * 
     * @param date
     * @return
     */
    List<Session> findByLastRequestBeforeAndEndTimeIsNullOrderByStartTimeDesc(
	    Date date);

    /**
     * 
     * @param ticket
     * @return
     */
    @Cacheable(SECURITY_TICKET_CACHE_NAME)
    Session findByTicketAndEndTimeIsNullAndAccountActiveTrue(String ticket);

    /**
     * 
     * @param ticket
     * @return
     */
    Session findByTicket(String ticket);

    /**
     * 
     * @param username
     * @param pageable
     * @return
     */
    Page<Session> findByAccountCredentialUsername(String username,
	    Pageable pageable);

}
