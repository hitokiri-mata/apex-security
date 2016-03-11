package com.apex.web.security.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.Date;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

//import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.apex.web.security.domain.Account;
import com.apex.web.security.domain.Role;
import com.apex.web.security.message.DefaultErrorMessage;
import com.apex.web.security.resource.AccountResource.UserLinks;
import com.apex.web.security.service.AccountService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author hitokiri
 *
 */
@Slf4j
// @Controller
// @RequestMapping(value = UserLinks.USER)
@ExposesResourceFor(Account.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class OldController {
    private final @NonNull ReloadableResourceBundleMessageSource messageSource;
    private final @NonNull AccountService userService;
    private final @NonNull EntityLinks entityLinks;

    /**
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@Valid @RequestBody Account user)
	    throws UserException {
	log.info("Starting to create a user in the system.");
	//
	Resource<Account> resource = new Resource<>(
		userService.saveOrUpdate(user));

	resource.add(entityLinks.linkForSingleResource(user).withSelfRel());
	return new ResponseEntity<>(resource, CREATED);

    }

    /**
     * 
     * @param usernme
     * @param password
     * @return
     */
    @RequestMapping(value = "/{username}/{password}", method = RequestMethod.GET)
    public HttpEntity<?> signIn(@PathParam("username") String usernme,
	    @PathParam("password") String password) {

	//
	Account mockUser = new Account();

	Role mockRole = new Role();
	mockRole.setDescription("some shit");
	mockRole.setName("Fuk You");

	// mockUser.setRole(mockRole);

	// mockUser.setPassword("some password");
	// mockUser.setUsername("some shit");
	// mockUser.setRole(mockRole);

	Resource<Account> resource = new Resource<>(mockUser);
	resource.add(entityLinks.linkForSingleResource(mockUser).withSelfRel());

	return ResponseEntity.ok(resource);
    }

    public ResponseEntity<?> logout() {
	return null;

    }

    /**
     * 
     * @param token
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> validateToken(
	    @RequestParam("token") String token) {
	return null;

    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByToken(
	    @PathParam("username") String token) {
	return null;

    }

    /**
     * 
     * @param exception
     * @return
     */

    @ResponseBody

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidException(
	    MethodArgumentNotValidException exception) {

	// log.error(ExceptionUtils.getMessage(exception)); // get a first field
	// error.
	ObjectError error = exception.getBindingResult().getAllErrors().get(0); 
	// get name
										// of
										// field
	// with validation error.
	FieldError fieldError = exception.getBindingResult().getFieldErrors()
		.get(0); // create default
	// system exception message
	return new ResponseEntity<>(
		new DefaultErrorMessage(new Date(),
			INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR
				.getReasonPhrase(),
		"" + MethodArgumentNotValidException.class.getCanonicalName(),
		error.getObjectName() + " " + fieldError.getField() + " "
			+ messageSource.getMessage(error.getDefaultMessage(),
				null, LocaleContextHolder.getLocale()),
		UserLinks.USER), INTERNAL_SERVER_ERROR);
    }

}
