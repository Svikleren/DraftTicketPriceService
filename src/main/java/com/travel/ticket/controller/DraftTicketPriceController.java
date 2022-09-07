package com.travel.ticket.controller;

import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.service.DraftTicketRequestValidationService;
import com.travel.ticket.service.OrderDraftCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DraftTicketPriceController {

    private final OrderDraftCalculationService orderDraftCalculationService;
    private final DraftTicketRequestValidationService draftTicketRequestValidationService;

    @PostMapping({"/draftTicketPrice"})
    public DraftTicketPriceResponseDto getDraftTicketPrice(
            @RequestBody DraftTicketPriceRequestDto draftTicketPriceRequestDto) {
        draftTicketRequestValidationService.validateDraftTicketPriceRequest(draftTicketPriceRequestDto);
        return orderDraftCalculationService.getOrderDraft(draftTicketPriceRequestDto);
    }
}
