package com.apex.web.security.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.apex.web.security.domain.Account;
import com.apex.web.security.rest.controller.AccountController;

public class AccountResourceAssembler
	implements ResourceAssembler<Account, Resource<Account>> {

    public static class AccountLinks {
	public static final String ACCOUNTS = "/account";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.hateoas.ResourceAssembler#toResource(java.lang.
     * Object)
     */
    @Override
    public Resource<Account> toResource(Account account) {
	return new Resource<Account>(account,
		linkTo(methodOn(AccountController.class)
			.getByPrincipal(account.getCredential().getUsername()))
				.withSelfRel());
    }
}
