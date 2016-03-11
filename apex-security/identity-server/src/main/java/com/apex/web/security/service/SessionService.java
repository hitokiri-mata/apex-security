package com.apex.web.security.service;

import java.util.Date;
import java.util.List;

import com.apex.web.security.domain.Session;

public interface SessionService {

    List<Session> getByLastRequestBefore(Date date);

    Session getByTicket(String ticket);

    Session saveOrUpdate(Session session);

}
