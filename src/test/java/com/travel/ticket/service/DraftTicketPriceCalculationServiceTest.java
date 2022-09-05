package com.travel.ticket.service;

import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.dto.PassengerDto;
import com.travel.ticket.external.CurrentVatService;
import com.travel.ticket.external.TicketBasePriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DraftTicketPriceCalculationServiceTest {

    @Mock
    private TicketBasePriceService ticketBasePriceService;

    @Mock
    private CurrentVatService currentVatService;

    @InjectMocks
    private DraftTicketPriceCalculationService service;

    @BeforeEach
    void setUp() {
        when(currentVatService.getVat()).thenReturn(BigDecimal.valueOf(0.21));
        when(ticketBasePriceService.getBasePrice(any())).thenReturn(BigDecimal.TEN);
    }

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