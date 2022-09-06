package com.travel.ticket.controller;

import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.service.DraftTicketPriceCalculationService;
import com.travel.ticket.service.DraftTicketRequestValidationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DraftTicketPriceController {

    private final DraftTicketPriceCalculationService draftTicketPriceCalculationService;
    private final DraftTicketRequestValidationService draftTicketRequestValidationService;

    @PostMapping({"/draftTicketPrice"})
    public DraftTicketPriceResponseDto getDraftTicketPrice(
            @RequestBody DraftTicketPriceRequestDto draftTicketPriceRequestDto) {
        draftTicketRequestValidationService.validateDraftTicketPriceRequest(draftTicketPriceRequestDto);
        return draftTicketPriceCalculationService.getAllTicketDraftPrice(draftTicketPriceRequestDto);
    }
}
