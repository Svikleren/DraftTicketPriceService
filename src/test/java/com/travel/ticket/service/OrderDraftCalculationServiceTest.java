package com.travel.ticket.service;

import com.travel.ticket.api.TaxRateApiAdapter;
import com.travel.ticket.api.TicketBasePriceApiAdapter;
import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.dto.PassengerRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDraftCalculationServiceTest {

    @Mock
    private TicketBasePriceApiAdapter ticketBasePriceApiAdapter;

    @Mock
    private TaxRateApiAdapter taxRateApiAdapter;

    @InjectMocks
    private OrderDraftCalculationService service;

    @BeforeEach
    void setUp() {
        when(taxRateApiAdapter.getVat()).thenReturn(BigDecimal.valueOf(0.21));
        when(ticketBasePriceApiAdapter.getBasePrice(any())).thenReturn(BigDecimal.TEN);
    }

    @Test
    void getOrderDraft() {
        PassengerRequestDto passengerRequestDtoAdult = PassengerRequestDto.builder()
                .luggageCount(2)
                .isChild(false)
                .build();
        PassengerRequestDto passengerRequestDtoChild = PassengerRequestDto.builder()
                .luggageCount(1)
                .isChild(true)
                .build();

        List<PassengerRequestDto> passengerRequestDtoList = new ArrayList<>();
        passengerRequestDtoList.add(passengerRequestDtoAdult);
        passengerRequestDtoList.add(passengerRequestDtoChild);

        DraftTicketPriceRequestDto request = DraftTicketPriceRequestDto.builder()
                .route("Vilnius")
                .passengerRequestDtoList(passengerRequestDtoList)
                .build();

        DraftTicketPriceResponseDto response = service.getOrderDraft(request);
        System.out.println(response);
    }
}