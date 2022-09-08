package com.travel.ticket.controller;

import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.service.OrderDraftCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DraftTicketPriceController {

    private final OrderDraftCalculationService orderDraftCalculationService;

    @PostMapping({"/draftTicketPrice"})
    public DraftTicketPriceResponseDto getDraftTicketPrice(
            @Valid @RequestBody DraftTicketPriceRequestDto draftTicketPriceRequestDto) {
        return orderDraftCalculationService.getOrderDraft(draftTicketPriceRequestDto);
    }

}
