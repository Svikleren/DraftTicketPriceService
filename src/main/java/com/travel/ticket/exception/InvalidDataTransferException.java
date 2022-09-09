package com.travel.ticket.exception;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class InvalidDataTransferException extends IOException {
    public InvalidDataTransferException(String message) {
        super(message);
    }
}
