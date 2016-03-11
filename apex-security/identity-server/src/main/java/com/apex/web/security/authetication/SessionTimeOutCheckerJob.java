package com.apex.web.security.authetication;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apex.web.security.domain.Session;
import com.apex.web.security.domain.Session.Agent;
import com.apex.web.security.service.SessionService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author hitokiri
 *
 */
@Slf4j
@Component
public class SessionTimeOutCheckerJob {

    @Autowired
    private SessionService sessionService;

    // @Value("durable.session.timeout")
    // private String sesstionTimeout;

    /**  
     * 
     */
    @Scheduled(cron = "* */20 * * * ?")
    public void check() {
	log.debug("checking .. the orphaned "
		+ "sessions on the system to be closed");
	sessionService
		.getByLastRequestBefore(
			new DateTime().minusMinutes(20).toDate())
		.forEach(session -> close(session));
    }

    /**
     * 
     * @param session
     */
    private void close(Session session) {
	session.setEndTime(new Date());
	session.setCloseBy(Agent.SYSTEM);
	sessionService.saveOrUpdate(session);
    }
}
