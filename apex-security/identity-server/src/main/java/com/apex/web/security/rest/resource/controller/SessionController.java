package com.apex.web.security.rest.resource.controller;

import static com.apex.web.security.rest.resource.SessionResourceAssembler.SessionLinks.SESSIONS;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apex.web.security.domain.Session;
import com.apex.web.security.rest.resource.SessionResourceAssembler;
import com.apex.web.security.service.SessionService;
import com.apex.web.security.validation.TicketValidator;
import com.apex.web.security.validation.ValidationException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * <p>
 * <i>View a Source Code</i>&nbsp;{@link CustomExceptionHandler}
 * </p>
 * 
 * @author <a href="cesar.mata@yuxipacific.com">Cesar a Mata de Avila</a>
 * @version %I%,%G%
 *
 */
@Controller
@RequestMapping(SESSIONS)
@ExposesResourceFor(Session.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class SessionController extends I18NExceptionControllerAdvice {
    private final @NonNull SessionResourceAssembler sessionResourceAssembler;
    private final @NonNull SessionService sessionService;
    private final @NonNull List<TicketValidator> validationChain = new ArrayList<>();

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "/{id}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getById(@PathVariable("id") Long id) {
	return ResponseEntity.ok(sessionResourceAssembler
		.toResource(sessionService.getById(id)));
    }

    /**
     * 
     * @param username
     * @return
     * @throws ValidationException
     */
    @RequestMapping(path = "/active/ticket/{ticket}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> validate(@PathVariable("ticket") String ticket) {
	System.out.println("@@@@@@@@@@@@@@@@@@@@ " + ticket);
	return ResponseEntity.ok(sessionResourceAssembler
		.toResource(sessionService.getActiveSessionByTicket(ticket)));
    }

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "/account/{principal}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getByAccountPrincipal(
	    @PathVariable("principal") String principal,
	    @PageableDefault Pageable pageable,
	    final PagedResourcesAssembler<Session> pagedResourceAssembler) {
	return ResponseEntity.ok(pagedResourceAssembler.toResource(
		sessionService.getByPrincipal(principal, pageable),
		sessionResourceAssembler));
    }

}
