package com.apex.web.security.service.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apex.web.security.service.LogiService;
import com.apex.web.security.service.ServiceException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author hitokiri
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class HttpLogiServiceImpl implements LogiService {
    private final String USER_AGENT = "Mozilla/5.0";
    private final String USER_AGENT_PROPERTY_NAME = "User-Agent";

    private @NonNull CloseableHttpClient HttpClient;

    /*
     * (non-Javadoc)
     * 
     * @see com.apex.web.security.service.LogiService#getSecurityKey(java.lang.
     * String)
     */
    @Override
    public String getSecurityKey(String request) throws ServiceException {
	// creating the http
	HttpGet method = new HttpGet(request);
	BufferedReader bufferReader = null;
	StringBuilder result = null;
	try {
	    method.addHeader(USER_AGENT_PROPERTY_NAME, USER_AGENT);
	    HttpResponse response = HttpClient.execute(method);
	    int requestHttpCode = response.getStatusLine().getStatusCode();
	    if (HTTP_OK != requestHttpCode) {
		throw new ServiceException(
			"Some error happened trying to performanc"
				+ "e the follow logic request'" + request
				+ "' the current http response code is '"
				+ requestHttpCode + "'");
	    }
	    // start to reading the http response content
	    bufferReader = new BufferedReader(
		    new InputStreamReader(response.getEntity().getContent()));
	    // reading http content
	    result = new StringBuilder();
	    String line = "";
	    while ((line = bufferReader.readLine()) != null) {
		result.append(line);
	    }
	    return result.toString();
	} catch (IOException e) {
	    throw new ServiceException("An error occurred trying "
		    + "to get the key security logic", e);
	} finally {
	    // closing the buffer reader with response information.
	    try {
		bufferReader.close();
	    } catch (IOException e) {
		log.error("an error occurred trying to "
			+ "close the following resource!" + e);
	    }
	    // release the http get method connection with Logi Analytic Server
	    method.releaseConnection();
	}

    }

}
