package com.travel.ticket.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
@Setter
public class ExternalServiceCallException extends IOException {

    HttpStatus errorStatus;
    String message;

    public ExternalServiceCallException(HttpStatus errorStatus, String message) {
        super(message);
        this.errorStatus = errorStatus;
        this.message = message;
    }
}
