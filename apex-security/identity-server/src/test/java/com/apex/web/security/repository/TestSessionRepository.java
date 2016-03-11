package com.apex.web.security.repository;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apex.web.security.domain.repository.SessionRepository;

public class TestSessionRepository extends AbstractIntegrationTest {

    private @Autowired SessionRepository sessionRepository;

    @Test
    public void testFindByLastRequestAfterDate() {
	DateTime now = new DateTime();
	now.minusMinutes(20);
	System.out.println("-->> " + sessionRepository
		.findByLastRequestBeforeAndEndTimeIsNull(now.toDate()));
    }

}
