package com.apex.web.security.rest.resource.controller;

import java.net.HttpURLConnection;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author hitokiri
 *
 */

@ControllerAdvice
public class I18NExceptionControllerAdvice {
    // containt message source instance
    private @Autowired ReloadableResourceBundleMessageSource messageSource;

    /**
     * 
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public DefaultMessageError handleEntityNotFoundException() {
	return new DefaultMessageError("RS00230",
		HttpURLConnection.HTTP_NOT_FOUND, messageSource.getMessage(
			"error.session.not.found", null, Locale.getDefault()));

    }

    /**
     * 
     * @author hitokiri
     *
     */
    @Data
    @AllArgsConstructor
    public static class DefaultMessageError {
	private String code;
	private int status;
	private String error;

    }

}
