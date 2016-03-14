package com.apex.web.security.rest.controller;

import static com.apex.web.security.resource.SessionResourceAssembler.SessionLinks.SESSIONS;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
import com.apex.web.security.resource.SessionResourceAssembler;
import com.apex.web.security.service.SessionService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author hitokiri
 *
 */
@Controller
@RequestMapping(SESSIONS)
@ExposesResourceFor(Session.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class SessionController {

    private final @NonNull SessionResourceAssembler sessionResourceAssembler;
    private final @NonNull SessionService sessionService;

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
