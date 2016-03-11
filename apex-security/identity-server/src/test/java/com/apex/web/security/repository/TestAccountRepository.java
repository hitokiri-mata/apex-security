package com.apex.web.security.repository;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apex.web.security.domain.Account;
import com.apex.web.security.domain.Person;
import com.apex.web.security.domain.Role;
import com.apex.web.security.domain.Account.Credential;
import com.apex.web.security.domain.Person.Address;
import com.apex.web.security.domain.Person.Gender;
import com.apex.web.security.domain.Person.Phone;
import com.apex.web.security.domain.Person.PhoneType;
import com.apex.web.security.domain.Role.Permission;
import com.apex.web.security.domain.repository.AccountRepository;

/**
 * Simple system component used for handled the persistent information about the
 * system user stored in different data repositories the application system.
 * <p>
 * <i>View a source code</i>&nbsp;<b>{@code UerRepository}</b>
 * </p>
 * 
 * @author <a href="mailto:cesar.mata@yuxipacific.com">Cesar A Mata de Avila</a>
 * @version %I%,%G% $Date
 *
 */
public class TestAccountRepository extends AbstractIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void createsCreditCard() {
	// save the information of the current user in the database.
	Account account = accountRepository.save(createUser());
	// retriver the stored information of the current user.
	Account result = accountRepository.findByCredentialUsername(
		account.getCredential().getUsername());
	//
	assertNotNull(result.getId());

    }

    public static Account createUser() {
	Person person = new Person();
	person.setFirstname("John");
	person.setLastname("Doe");
	person.setGender(Gender.MALE);
	//
	List<Phone> phones = new ArrayList<>();
	Phone phone = new Phone();
	phone.setAreaCode("+57");
	phone.setNumber("3017839037");
	phone.setPhoneType(PhoneType.MOBILE);
	phones.add(phone);
	person.setPhones(phones);
	//
	try {
	    person.setBirthday(
		    new SimpleDateFormat("dd-MM-yyyy").parse("26-06-1978"));
	} catch (ParseException e) {
	    throw new RuntimeException(
		    "Ocurrio un error transformando la fecha en el formato dado");
	}
	// create a person address;
	Address address = new Address();
	address.setCity("Medellin");
	address.setStreet("42#62-15 sur Edifio Kandala Torre 4 Apart 502");
	address.setZipCode("405030");
	// set address to current person.
	person.setAddress(address);
	// setting Administrator Roles value.
	List<Role> roles = new ArrayList<>();
	Role role = new Role();
	role.setName("Administrator");
	role.setDescription("Superpowerful user in the system ");
	//
	List<Permission> permissions = new ArrayList<>();
	permissions.add(Permission.WRITE);
	permissions.add(Permission.READ);
	permissions.add(Permission.DELETE);
	permissions.add(Permission.EDIT);
	//
	role.setPermissions(permissions);
	roles.add(role);
	//
	Account account = new Account();
	//
	Credential credential = new Credential();
	credential.setUsername("cmata");
	credential.setPassword("cmata123");
	//
	account.setCredential(credential);
	account.setPerson(person);
	account.setRoles(roles);
	account.setPerson(person);
	//
	// Session session = new Session();
	// session.setAccount(account);
	// session.setIp("10.22.100.9");
	// session.setSecurityToken(new SecurityToken());

	// List<Session> sessions = new ArrayList<>();
	// sessions.add(session);

	// account.setSession(sessions);
	//
	return account;

    }

}
