package com.apex.web.security.resource;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;

import com.apex.web.security.domain.Account;
import com.apex.web.security.domain.Person;
import com.apex.web.security.resource.AccountResourceAssembler.AccountResource;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class AccountResourceAssembler
	implements ResourceAssembler<Account, AccountResource> {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class AccountResource extends ResourceSupport {
	private String principal;
	private Person person;
	private boolean active;

	public static class AccountLinks {
	    public static final String ACCOUNTS = "/account";
	}

	/**
	 * 
	 * @param account
	 */
	public AccountResource(Account account) {
	    this.person = account.getPerson();
	    this.principal = account.getCredential().getUsername();
	    this.active = account.isActive();
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.hateoas.ResourceAssembler#toResource(java.lang.
     * Object)
     */
    @Override
    public AccountResource toResource(Account account) {
	AccountResource resource = new AccountResource(account);
//	resource.add(linkTo(methodOn(RoleController.class)
//		.getByPrincipal(account.getCredential().getUsername()))
//			.withRel("roles"));
	return resource;
    }
}
