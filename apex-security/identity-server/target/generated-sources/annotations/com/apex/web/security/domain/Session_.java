package com.apex.web.security.domain;

import com.apex.web.security.domain.Session.Agent;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Session.class)
public abstract class Session_ extends com.apex.web.security.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Session, String> ticket;
	public static volatile SingularAttribute<Session, Date> lastRequest;
	public static volatile SingularAttribute<Session, String> remoteIPAddress;
	public static volatile SingularAttribute<Session, Agent> closeBy;
	public static volatile SingularAttribute<Session, Date> startTime;
	public static volatile SingularAttribute<Session, Date> endTime;
	public static volatile SingularAttribute<Session, Account> account;

}

