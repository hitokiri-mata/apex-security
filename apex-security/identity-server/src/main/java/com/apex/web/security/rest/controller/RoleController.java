package com.apex.web.security.rest.controller;

import static com.apex.web.security.resource.RoleResourceAssembler.RoleLinks.ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apex.web.security.domain.Role;
import com.apex.web.security.resource.RoleResourceAssembler;
import com.apex.web.security.service.RoleService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ROLE)
@ExposesResourceFor(Role.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class RoleController {

    private final @NonNull RoleService roleService;

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "/{id}", method = GET)
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
	return ResponseEntity.ok(new RoleResourceAssembler()
		.toResource(roleService.getById(id)));
    }

    /**
     * 
     * @param username
     * @return
     */
    @RequestMapping(path = "/principal/{username}", method = GET)
    public ResponseEntity<?> getByPrincipal(
	    @PathVariable("username") String username) {
	log.debug("getting the system roles asigned to principal '" + username
		+ "'");
	List<Resource<Role>> roleResources = new ArrayList<>();
	roleService.getByPrincipal(username).forEach(role -> roleResources
		.add(new RoleResourceAssembler().toResource(role)));
	return ResponseEntity.ok(roleResources);
    }

}
