package com.travel.ticket.controller;

import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.service.OrderDraftCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DraftTicketPriceController {

    private final OrderDraftCalculationService orderDraftCalculationService;

    @PostMapping({"/draft-ticket-price"})
    public DraftTicketPriceResponseDto getDraftTicketPrice(
            @Valid @RequestBody DraftTicketPriceRequestDto draftTicketPriceRequestDto) {
        log.info(String.format("Draft ticket price API: request body %s received",
                draftTicketPriceRequestDto.toString()));
        return orderDraftCalculationService.getOrderDraft(draftTicketPriceRequestDto);
    }

}
