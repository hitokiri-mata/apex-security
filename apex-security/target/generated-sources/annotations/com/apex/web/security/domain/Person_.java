package com.apex.web.security.domain;

import com.apex.web.security.domain.Person.Address;
import com.apex.web.security.domain.Person.Gender;
import com.apex.web.security.domain.Person.Phone;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ extends com.apex.web.security.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Person, Date> birthday;
	public static volatile SingularAttribute<Person, String> firstname;
	public static volatile SingularAttribute<Person, Address> address;
	public static volatile SingularAttribute<Person, Gender> gender;
	public static volatile ListAttribute<Person, Phone> phones;
	public static volatile SingularAttribute<Person, Account> account;
	public static volatile SingularAttribute<Person, String> lastname;

}

