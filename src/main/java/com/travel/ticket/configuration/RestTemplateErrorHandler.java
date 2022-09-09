package com.travel.ticket.configuration;

import com.travel.ticket.exception.InvalidDataTransferException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().is4xxClientError()) {
            throw new InvalidDataTransferException("Client error received while calling external service");
        }
        if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new InvalidDataTransferException("Server error happened while calling external service");
        }
    }
}
