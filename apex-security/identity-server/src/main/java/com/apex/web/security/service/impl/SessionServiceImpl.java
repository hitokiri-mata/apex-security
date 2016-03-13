package com.apex.web.security.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apex.web.security.domain.Session;
import com.apex.web.security.domain.repository.SessionRepository;
import com.apex.web.security.service.SessionService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author hitokiri
 *
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class SessionServiceImpl implements SessionService {
    // containt current intance of the session reppsitory
    private @NonNull SessionRepository sessionRepository;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.SessionService#findByLastRequestBefore(java
     * .util.Date)
     */
    @Override
    public List<Session> getByLastRequestBefore(Date date) {
	return sessionRepository.findByLastRequestBeforeAndEndTimeIsNull(date);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.apex.web.security.service.SessionService#getByTicket(java.lang.
     * String)
     */
    @Override
    public Session getByTicket(String ticket) {
	return sessionRepository.findByTicket(ticket);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.SessionService#saveOrUpdate(com.apex.web.
     * security.domain.Session)
     */
    @Override
    public Session saveOrUpdate(Session session) {
	return sessionRepository.save(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.SessionService#getByPrincipal(java.lang.
     * String, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<Session> getByPrincipal(String principal, Pageable pageable) {
	return sessionRepository.findByAccountCredentialUsername(principal,
		pageable);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.apex.web.security.service.SessionService#getById(java.lang.Long)
     */
    @Override
    public Session getById(Long id) {
	return sessionRepository.findOne(id);
    }

}
