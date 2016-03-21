package com.apex.web.security.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apex.web.security.domain.Session;

/**
 * 
 * @author hitokiri
 *
 */
public interface SessionService {

    Session getById(Long id);

    Page<Session> getByPrincipal(String principal, Pageable pageable);

    List<Session> getByLastRequestBefore(Date date);

    Session getByTicket(String ticket);

    Session saveOrUpdate(Session session);

}
