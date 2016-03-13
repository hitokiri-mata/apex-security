package com.apex.web.security.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.apex.web.security.domain.Permission;
import com.apex.web.security.rest.controller.PermissionController;

/**
 * 
 * @author hitokiri
 *
 */
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
