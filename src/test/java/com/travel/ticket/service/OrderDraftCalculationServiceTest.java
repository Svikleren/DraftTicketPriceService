package com.travel.ticket.service;

import com.travel.ticket.api.TaxRateApiAdapter;
import com.travel.ticket.api.TicketBasePriceApiAdapter;
import com.travel.ticket.common.TicketType;
import com.travel.ticket.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDraftCalculationServiceTest {

    @Mock
    private TicketBasePriceApiAdapter ticketBasePriceApiAdapter;

    @Mock
    private TaxRateApiAdapter taxRateApiAdapter;

    @Mock
    private TicketDraftPriceCalculationService ticketDraftPriceCalculationService;

    @InjectMocks
    private OrderDraftCalculationService service;

    private final String route = "Route";

    @BeforeEach
    void setUp() {
        when(taxRateApiAdapter.getVat()).thenReturn(BigDecimal.valueOf(0.21));
        when(ticketBasePriceApiAdapter.getBasePrice(route)).thenReturn(BigDecimal.TEN);
    }

    @Test
    void getOrderDraft() {

        List<TicketResponseDto> ticketsForAdult = new ArrayList<>();
        ticketsForAdult.add(createExpectedTicketResponseDto(TicketType.ADULT));
        ticketsForAdult.add(createExpectedTicketResponseDto(TicketType.LUGGAGE));

        List<TicketResponseDto> ticketsForChild = new ArrayList<>();
        ticketsForChild.add(createExpectedTicketResponseDto(TicketType.CHILD));

        DraftTicketPriceRequestDto requestDto = createDraftTicketPriceRequest();

        when(ticketDraftPriceCalculationService.getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(0)), eq(BigDecimal.TEN), eq(BigDecimal.valueOf(0.21))))
                .thenReturn(ticketsForAdult);

        when(ticketDraftPriceCalculationService.getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(1)), eq(BigDecimal.TEN), eq(BigDecimal.valueOf(0.21))))
                .thenReturn(ticketsForChild);

        DraftTicketPriceResponseDto response = service.getOrderDraft(requestDto);

        DraftTicketPriceResponseDto expectedResponse = createExpectedResponse(BigDecimal.valueOf(36.30));
        expectedResponse.setPassengerDtoList(
                Arrays.asList(
                        createExpectedPassenger(ticketsForAdult, BigDecimal.valueOf(24.20)),
                        createExpectedPassenger(ticketsForChild, BigDecimal.valueOf(12.10))
                ));

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expectedResponse);


        verify(taxRateApiAdapter).getVat();
        verify(ticketBasePriceApiAdapter).getBasePrice(route);

        verify(ticketDraftPriceCalculationService).getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(0)), eq(BigDecimal.TEN), eq(BigDecimal.valueOf(0.21)));

        verify(ticketDraftPriceCalculationService).getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(1)), eq(BigDecimal.TEN), eq(BigDecimal.valueOf(0.21)));

        verifyNoMoreInteractions(taxRateApiAdapter, ticketBasePriceApiAdapter, ticketDraftPriceCalculationService);
    }

    private DraftTicketPriceRequestDto createDraftTicketPriceRequest() {
        PassengerRequestDto passengerRequestDtoAdult = PassengerRequestDto.builder()
                .luggageCount(1)
                .isChild(false)
                .build();
        PassengerRequestDto passengerRequestDtoChild = PassengerRequestDto.builder()
                .luggageCount(0)
                .isChild(true)
                .build();

        List<PassengerRequestDto> passengerRequestDtoList = new ArrayList<>();
        passengerRequestDtoList.add(passengerRequestDtoAdult);
        passengerRequestDtoList.add(passengerRequestDtoChild);

        return DraftTicketPriceRequestDto.builder()
                .route(route)
                .passengerRequestDtoList(passengerRequestDtoList)
                .build();

    }

    private DraftTicketPriceResponseDto createExpectedResponse(BigDecimal totalOrderPrice) {
        return DraftTicketPriceResponseDto.builder()
                .route(route)
                .vat(BigDecimal.valueOf(0.21))
                .totalOrderPrice(totalOrderPrice)
                .build();
    }

    private PassengerResponseDto createExpectedPassenger(List<TicketResponseDto> ticketResponseDtoList, BigDecimal totalOrderPrice) {
        return PassengerResponseDto.builder()
                .totalPassengerOrderPrice(totalOrderPrice)
                .ticketResponseDtoList(ticketResponseDtoList)
                .build();
    }

    private TicketResponseDto createExpectedTicketResponseDto(TicketType ticketType) {
        return TicketResponseDto.builder()
                .ticketType(ticketType)
                .ticketPriceWithoutVat(BigDecimal.TEN)
                .vat(BigDecimal.valueOf(2.10))
                .ticketTotalPrice(BigDecimal.valueOf(12.10))
                .build();
    }
}