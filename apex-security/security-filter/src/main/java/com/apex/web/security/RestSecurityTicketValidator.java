package com.apex.web.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author hitokiri
 *
 */
public class RestSecurityTicketValidator implements SecurityTicketValidator {
    private static final String METHOD_GET = "GET";

    private String serverAddress;

    public void setServerAddress(String serverAddress) {
	this.serverAddress = serverAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.SecurityTicketValidator#validate(java.lang.String)
     */
    @Override
    public void validate(String securityTicket) {
	BufferedReader bufferReader = null;
	HttpURLConnection connection = null;
	try {
	    // creating the URL for the raw rest service call
	    URL url = new URL(this.serverAddress);
	    // create url connection to call validation rest service.
	    connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod(METHOD_GET);
	    connection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
	    connection.addRequestProperty("User-Agent", "Mozilla");
	    connection.setRequestProperty("Accept", "application/json");

	    connection.addRequestProperty("Cookie",
		    "JSESSIONID=4031AE167627A558E62400E094FF444E; XSRF-TOKEN=931f478f-3204-4091-a2ac-4731b4bf691c");
	    // getting the webservice response code.
	    System.out.println("response code " + connection.getResponseCode());
	    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
		throw new RuntimeException("Failed : HTTP error code : "
			+ connection.getResponseCode());
	    }
	    System.out.println("-->> -->>  " + connection.getResponseCode());
	    // getting the content from the URL
	    bufferReader = new BufferedReader(
		    new InputStreamReader((connection.getInputStream())));
	    // print the validation service content.
	    String line;
	    System.out.println("Output from Server .... \n");
	    while ((line = bufferReader.readLine()) != null) {
		System.out.println(line);
	    }
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    connection.disconnect();
	}

    }

    public static void main(String[] args) {
	RestSecurityTicketValidator validator = new RestSecurityTicketValidator();
	validator.setServerAddress(
		"http://localhost:9000/sessions/active/ticket/ac8e78a4-0a36-403d-a62c-540e95fe614b");
	validator.validate("");
    }

}
