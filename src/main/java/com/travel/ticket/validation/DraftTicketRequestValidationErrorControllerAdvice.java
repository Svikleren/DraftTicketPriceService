package com.travel.ticket.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DraftTicketRequestValidationErrorControllerAdvice {

/*    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    DraftTicketRequestValidationErrorResponse getConstraintViolationExceptionResponse(
            ConstraintViolationException exception) {
        DraftTicketRequestValidationErrorResponse error = new DraftTicketRequestValidationErrorResponse();
        exception.getConstraintViolations()
                .forEach(violation -> error.getRequestViolationList().add(DraftTicketRequestViolation.builder()
                        .fieldName(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build()));
        return error;
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<String> getMethodArgumentNotValidExceptionResponse(
            MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> sb.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(sb.toString());
    }

}
