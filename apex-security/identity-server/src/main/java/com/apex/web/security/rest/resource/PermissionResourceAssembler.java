package com.apex.web.security.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.apex.web.security.domain.Permission;
import com.apex.web.security.rest.resource.controller.PermissionController;

/**
 * 
 * @author hitokiri
 *
 */
@Component
public class PermissionResourceAssembler
	implements ResourceAssembler<Permission, Resource<Permission>> {

    public static class RoleLinks {
	public static final String PERMISSIONS = "/permissions";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.hateoas.ResourceAssembler#toResource(java.lang.
     * Object)
     */
    @Override
    public Resource<Permission> toResource(Permission permission) {
	return new Resource<Permission>(permission,
		linkTo(methodOn(PermissionController.class)
			.getById(permission.getId())).withSelfRel());
    }

}
