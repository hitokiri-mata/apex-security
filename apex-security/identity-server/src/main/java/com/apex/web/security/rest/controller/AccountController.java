package com.apex.web.security.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apex.web.security.domain.Account;
import com.apex.web.security.service.AccountService;
import com.apex.web.security.validation.TestTicketValidator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author hitokiri
 *
 */
@Slf4j
@Controller
@RequestMapping("/account")
@ExposesResourceFor(Account.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class AccountController {

    private final @NonNull AccountService accountService;
    private final @NonNull EntityLinks entityLinks;
    private final @NonNull TestTicketValidator ticketValidator;

    /**
     * 
     * @param ticket
     * @return
     */
    @RequestMapping(path = "/ticket/{ticket}", method = GET)
    public ResponseEntity<?> getAccountByToken(
	    @PathVariable("ticket") String ticket) {
	log.debug("");
	ticketValidator.validate(ticket);
	Account account = accountService.getBySecurityTicket(ticket);

	return ResponseEntity.ok(new Resource<>(account));
    }

}
