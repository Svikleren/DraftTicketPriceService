package com.travel.ticket.validation;

import com.travel.ticket.exception.ExternalServiceCallException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DraftTicketRequestValidationErrorControllerAdvice {

    /**
     * This method catch all MethodArgumentNotValidException thrown when user request violates
     * the rules (i.e. negative luggage count, empty route) and allow to provide meaningful explanation to user
     *
     * @param exception - MethodArgumentNotValidException
     * @return Response entity with needed status and message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> onMethodArgumentNotValidExceptionResponse(
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

    /**
     * This method can be used to catch all ExternalServiceCallExceptions thrown in
     * RestTemplateErrorHandler and return more meaningful error message to user or additional exception handling
     * logic can be added here
     *
     * @param exception - ExternalServiceCallException - thrown when we got errors while calling external services
     * @return Response entity with needed status and message
     */
    @ExceptionHandler(ExternalServiceCallException.class)
    ResponseEntity<String> onExternalServiceClientError(
            ExternalServiceCallException exception) {
        return ResponseEntity
                .status(exception.getErrorStatus())
                .body(exception.getMessage());
    }

}
