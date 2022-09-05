package com.travel.ticket.service;

import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.dto.PassengerDto;
import org.junit.jupiter.api.Test;

class PriceCalculationServiceTest {

    private PriceCalculationService service = new PriceCalculationService();

    @Test
    void getAllTicketDraftPrice() {
        DraftTicketPriceRequestDto request = new DraftTicketPriceRequestDto("Vilnius");

        PassengerDto passengerDtoAdult = new PassengerDto();
        PassengerDto passengerDtoChild = new PassengerDto();

        passengerDtoAdult.setLuggageCount(2);
        passengerDtoAdult.setChild(false);

        passengerDtoChild.setLuggageCount(1);
        passengerDtoChild.setChild(true);

        request.getPassengerDtoList().add(passengerDtoAdult);
        request.getPassengerDtoList().add(passengerDtoChild);

        DraftTicketPriceResponseDto response = service.getAllTicketDraftPrice(request);
        System.out.println(response);
    }
}