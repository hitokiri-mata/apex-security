package com.apex.web.security.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.apex.web.security.domain.Role;
import com.apex.web.security.rest.controller.RoleController;

/**
 * 
 * @author hitokiri
 *
 */
public class RoleResourceAssembler
	implements ResourceAssembler<Role, Resource<Role>> {

    public static class RoleLinks {
	public static final String ROLE = "/role";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.hateoas.ResourceAssembler#toResource(java.lang.
     * Object)
     */
    @Override
    public Resource<Role> toResource(Role role) {
	Resource<Role> resource = new Resource<>(role);
	resource.add(
		linkTo(methodOn(RoleController.class).getById(role.getId()))
			.withSelfRel());
	return resource;
    }

}
