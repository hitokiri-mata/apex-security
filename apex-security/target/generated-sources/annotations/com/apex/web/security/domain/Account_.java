package com.apex.web.security.domain;

import com.apex.web.security.domain.Account.Credential;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ extends com.apex.web.security.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Account, Credential> credential;
	public static volatile SingularAttribute<Account, Person> person;
	public static volatile ListAttribute<Account, Session> session;
	public static volatile ListAttribute<Account, Role> roles;
	public static volatile SingularAttribute<Account, Boolean> active;

}

