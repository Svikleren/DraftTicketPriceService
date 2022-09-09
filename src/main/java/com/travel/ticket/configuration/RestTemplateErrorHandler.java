package com.travel.ticket.configuration;

import com.travel.ticket.exception.ExternalServiceCallException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    /**
     * Method to define which ClientHttpResponse contains errors
     *
     * @param httpResponse the response with the error
     * @throws IOException
     */
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().isError();
    }

    /**
     * Method to handle all ClientHttpResponse responses defined as errors in 'hasError' method.
     * Allow to convert common response to specific exception to handle it further
     *
     * @param httpResponse the response with the error
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        throw new ExternalServiceCallException(httpResponse.getStatusCode(), httpResponse.getStatusCode().toString());
    }
}
