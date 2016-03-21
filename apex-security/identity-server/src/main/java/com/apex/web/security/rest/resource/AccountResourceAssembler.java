package com.apex.web.security.rest.resource;

import static com.apex.web.security.rest.resource.RoleResourceAssembler.RoleLinks.ROLES;
import static com.apex.web.security.rest.resource.SessionResourceAssembler.SessionLinks.SESSIONS;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.apex.web.security.domain.Account;
import com.apex.web.security.rest.resource.controller.AccountController;
import com.apex.web.security.rest.resource.controller.RoleController;
import com.apex.web.security.rest.resource.controller.SessionController;

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
	Resource<Account> resource = new Resource<Account>(account,
		linkTo(methodOn(AccountController.class)
			.getByPrincipal(account.getCredential().getUsername()))
				.withSelfRel());
	resource.add(
		linkTo(methodOn(SessionController.class).getByAccountPrincipal(
			account.getCredential().getUsername(), null, null))
				.withRel(SESSIONS));
	resource.add(linkTo(methodOn(RoleController.class).getByPrincipal(
		account.getCredential().getUsername(), null, null))
			.withRel(ROLES));
	return resource;
    }
}
