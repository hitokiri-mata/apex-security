package com.apex.web.security.validation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.apex.web.security.domain.Account;
import com.apex.web.security.exception.NonActiveAccountException;
import com.apex.web.security.service.AccountService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class RestTicketValidator implements TestTicketValidator {
    private @Autowired HttpServletRequest request;
    private final @NonNull MessageSource messages;
    private final @NonNull AccountService accountService;

    /*
     * (non-Javadoc)
     * 
     * @see com.apex.web.security.validation.TicketValidator#validate(java.lang.
     * String)
     */
    @Override
    public void validate(String ticket) {
	// getting user account by security ticket.
	Account account = accountService.getBySecurityTicket(ticket);
	// validate if current user account is active into the system.
	assert!account.isActive() : new NonActiveAccountException();
	// validating if have a active durable session into the system.
	assert account
		.getActiveSession() == null : new SessionAuthenticationException(
			"");
	System.out.println("-->>  -->> -->> " + request.getRemoteAddr());
	// validating if
	/*
	 * assert account.getActiveSession() .getEndTime() != null : new
	 * SessionAuthenticationException( messages.getMessage(
	 * "DigestAuthenticationFilter.usernameNotFound", new Object[] { "" },
	 * LocaleContextHolder.getLocale()));
	 */
	//
    }

}
