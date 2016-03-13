package com.apex.web.security.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static com.apex.web.security.resource.PermissionResourceAssembler.RoleLinks.PERMISSIONS;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.apex.web.security.domain.Role;
import com.apex.web.security.rest.controller.PermissionController;
import com.apex.web.security.rest.controller.RoleController;

/**
 * 
 * @author hitokiri
 *
 */
@Component
public class RoleResourceAssembler
	implements ResourceAssembler<Role, Resource<Role>> {

    public static class RoleLinks {
	public static final String ROLES = "/roles";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.hateoas.ResourceAssembler#toResource(java.lang.
     * Object)
     */
    @Override
    public Resource<Role> toResource(Role role) {
	Resource<Role> resource = new Resource<Role>(role,
		linkTo(methodOn(RoleController.class).getById(role.getId()))
			.withSelfRel());
	resource.add(linkTo(methodOn(PermissionController.class)
		.getByRoleId(role.getId(), null, null)).withRel(PERMISSIONS));
	return resource;
    }

}
