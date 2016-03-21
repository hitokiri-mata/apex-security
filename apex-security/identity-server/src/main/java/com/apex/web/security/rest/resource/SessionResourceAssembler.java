package com.apex.web.security.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.apex.web.security.domain.Session;
import com.apex.web.security.rest.resource.controller.SessionController;

/**
 * 
 * @author hitokiri
 *
 */
@Component
public class SessionResourceAssembler
	implements ResourceAssembler<Session, Resource<Session>> {

    public static class SessionLinks {
	public static final String SESSIONS = "/sessions";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.hateoas.ResourceAssembler#toResource(java.lang.
     * Object)
     */
    @Override
    public Resource<Session> toResource(Session session) {
	return new Resource<Session>(session, linkTo(
		methodOn(SessionController.class).getById(session.getId()))
			.withSelfRel());
    }

}
