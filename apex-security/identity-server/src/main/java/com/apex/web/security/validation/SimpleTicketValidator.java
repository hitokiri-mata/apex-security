package com.apex.web.security.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.apex.web.security.domain.Account;
import com.apex.web.security.domain.Account.Credential;
import com.apex.web.security.exception.ValidationException;
import com.apex.web.security.service.AccountService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author hitokiri
 *
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class SimpleTicketValidator implements TicketValidator {
    // setting the user account service intance
    private @NonNull AccountService accountService;

    /*
     * (non-Javadoc)
     * 
     * @see com.apex.web.security.validation.TicketValidator#validate(java.lang.
     * String)
     */
    public void validate(String ticket) throws ValidationException {
	// getting user account by security ticket.
	Account account = accountService.getBySecurityTicket(ticket);
	// validate if the current session account exist.
	if (account == null) {
	    throw new ValidationException(
		    "Not a user account associated with the security ticket '"
			    + ticket + "' was found");
	}
	Credential credential = account.getCredential();
	// validate user credential.
	if (credential == null) {
	    throw new ValidationException(
		    "No credentials was found for current user account");
	}
	// validate if current user account is active into the system.
	if (!account.isActive()) {
	    throw new ValidationException("The user account '"
		    + credential.getUsername() + "' is not active");
	}
	// validating if have a active durable session into the system.
	if (account.getActiveSession() == null) {
	    throw new ValidationException(
		    "Not user session active was found, for the user account '"
			    + credential.getUsername() + "'");
	}
    }
}
