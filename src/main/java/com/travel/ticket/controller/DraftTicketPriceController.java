package com.travel.ticket.controller;

import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.service.DraftTicketRequestValidationService;
import com.travel.ticket.service.DraftTicketPriceCalculationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DraftTicketPriceController {

    private final DraftTicketPriceCalculationService draftTicketPriceCalculationService;
    private final DraftTicketRequestValidationService draftTicketRequestValidationService;

    @PostMapping({"/draftTicketPrice"})
    public ResponseEntity<DraftTicketPriceResponseDto> getDraftTicketPrice(
            @RequestBody DraftTicketPriceRequestDto draftTicketPriceRequestDto) {
        draftTicketRequestValidationService.validateDraftTicketPriceRequest(draftTicketPriceRequestDto);
        return new ResponseEntity<>(draftTicketPriceCalculationService.getAllTicketDraftPrice(draftTicketPriceRequestDto), HttpStatus.OK);
    }
}
