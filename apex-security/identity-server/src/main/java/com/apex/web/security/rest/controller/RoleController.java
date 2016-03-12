package com.apex.web.security.rest.controller;

import static com.apex.web.security.resource.RoleResourceAssembler.RoleLinks.ROLES;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apex.web.security.domain.Role;
import com.apex.web.security.domain.Role.Permission;
import com.apex.web.security.resource.RoleResourceAssembler;
import com.apex.web.security.service.RoleService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ROLES)
@ExposesResourceFor(Role.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class RoleController {
    private final @NonNull RoleService roleService;
    private final @NonNull RoleResourceAssembler roleResourceAssembler;

    /**
     * 
     * @param pageable
     * @param pagedResourceAssembler
     * @return
     * 
     */
    @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getAll(@PageableDefault Pageable pageable,
	    final PagedResourcesAssembler<Role> pagedResourceAssembler) {
	return ResponseEntity.ok(pagedResourceAssembler.toResource(
		roleService.getAll(pageable), roleResourceAssembler));
    }

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "/{id}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getById(@PathVariable("id") Long id) {
	return ResponseEntity
		.ok(roleResourceAssembler.toResource(roleService.getById(id)));
    }

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "/{id}/permissions", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getByPermissionByRoleId(@PathVariable("id") Long id) {
	Role role = roleService.getById(id);
	List<Resource<Permission>> permissions = new ArrayList<>();
	role.getPermissions()
		.forEach(permission -> permissions
			.add(new Resource<Permission>(permission,
				linkTo(methodOn(RoleController.class)
					.getByPermissionByRoleId(role.getId()))
						.withSelfRel())));
	return ResponseEntity.ok(permissions);
    }

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "/principal/{username}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getByPrincipal(
	    @PathVariable("username") String username,
	    @PageableDefault Pageable pageable,
	    PagedResourcesAssembler<Role> pagedResourceAssembler) {
	log.debug("getting the system roles asigned to principal '" + username
		+ "'");
	return ResponseEntity.ok(pagedResourceAssembler.toResource(
		roleService.getByPrincipal(username, pageable),
		roleResourceAssembler));
    }

}
