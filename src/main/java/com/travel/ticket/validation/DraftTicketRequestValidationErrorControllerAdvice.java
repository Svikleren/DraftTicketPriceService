package com.travel.ticket.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DraftTicketRequestValidationErrorControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> getMethodArgumentNotValidExceptionResponse(
            MethodArgumentNotValidException exception) {
        StringBuilder fieldsWithErrors = new StringBuilder();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> fieldsWithErrors
                        .append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(fieldsWithErrors.toString());
    }

}
