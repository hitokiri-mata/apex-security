package com.apex.web.security.rest.resource.controller;

import static com.apex.web.security.rest.resource.AccountResourceAssembler.AccountLinks.ACCOUNTS;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apex.web.security.domain.Account;
import com.apex.web.security.rest.resource.AccountResourceAssembler;
import com.apex.web.security.service.AccountService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author hitokiri
 *
 */

@Controller
@RequestMapping(ACCOUNTS)
@ExposesResourceFor(Account.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class AccountController {
    //
    private final @NonNull AccountService accountService;

    /**
     * 
     * @param ticket
     * @return
     */
    @RequestMapping(path = "/{principal}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getByPrincipal(
	    @PathVariable("principal") String principal) {
	return ResponseEntity.ok(new AccountResourceAssembler()
		.toResource(accountService.getByPrincipal(principal)));
    }

    /**
     * 
     * @param ticket
     * @return
     */
    @RequestMapping(path = "/ticket/{ticket}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getAccountByToken(
	    @PathVariable("ticket") String ticket) {
	return ResponseEntity.ok(new AccountResourceAssembler()
		.toResource(accountService.getBySecurityTicket(ticket)));
    }

}
