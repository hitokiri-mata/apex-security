package com.apex.web.security.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.apex.web.security.domain.Session;

/*
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
