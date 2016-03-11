package com.apex.web.security.validation;

import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RemoteIPAddressChecker {
    /**
     * Simple method used to validate the IP request address is from Authorized
     * Range.
     * 
     * @param address
     * @return
     */
    public boolean isValid(String address) {
	log.debug("Validating.. if the current ip address '" + address
		+ "' comming from the Authorized IP address range ");
	return new IpAddressMatcher("192.168.1.0/24").matches(address);
    }
}