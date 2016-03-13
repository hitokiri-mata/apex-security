package com.apex.web.security.rest.controller;

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

import com.apex.web.security.domain.Permission;
import com.apex.web.security.domain.Role;
import com.apex.web.security.resource.PermissionResourceAssembler;
import com.apex.web.security.service.PermissionService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@ExposesResourceFor(Role.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class PermissionController {

    private final @NonNull PermissionResourceAssembler permissionResourceAssembler;
    private final @NonNull PermissionService permissionService;

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "permission/{id}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getById(@PathVariable("id") Long id) {
	return ResponseEntity.ok(permissionResourceAssembler
		.toResource(permissionService.getById(id)));
    }

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "role/{id}/permissions", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getByRoleId(@PathVariable("id") Long id,
	    @PageableDefault Pageable pageable,
	    PagedResourcesAssembler<Permission> pagedResourceAssembler) {
	return ResponseEntity.ok(pagedResourceAssembler.toResource(
		permissionService.getByRoleId(id, pageable),
		permissionResourceAssembler));
    }

}
