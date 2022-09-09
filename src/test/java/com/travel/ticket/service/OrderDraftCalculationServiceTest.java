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

import static com.travel.ticket.common.TestConstants.ADULT_TICKET_BASE_PRICE;
import static com.travel.ticket.common.TestConstants.VAT;
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
        when(taxRateApiAdapter.getVat()).thenReturn(VAT);
        when(ticketBasePriceApiAdapter.getBasePrice(route)).thenReturn(ADULT_TICKET_BASE_PRICE);
    }

    @Test
    void getOrderDraft() {
        List<TicketResponseDto> ticketsForAdult = new ArrayList<>();
        ticketsForAdult.add(createExpectedTicketResponseDto(TicketType.ADULT, ADULT_TICKET_BASE_PRICE, BigDecimal.valueOf(2.10)));
        ticketsForAdult.add(createExpectedTicketResponseDto(TicketType.LUGGAGE, BigDecimal.valueOf(3), BigDecimal.valueOf(0.63)));

        List<TicketResponseDto> ticketsForChild = new ArrayList<>();
        ticketsForChild.add(createExpectedTicketResponseDto(TicketType.CHILD, BigDecimal.valueOf(5), BigDecimal.valueOf(1.05)));

        DraftTicketPriceRequestDto requestDto = createDraftTicketPriceRequest();

        when(ticketDraftPriceCalculationService.getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(0)), eq(ADULT_TICKET_BASE_PRICE), eq(VAT)))
                .thenReturn(ticketsForAdult);

        when(ticketDraftPriceCalculationService.getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(1)), eq(ADULT_TICKET_BASE_PRICE), eq(VAT)))
                .thenReturn(ticketsForChild);

        DraftTicketPriceResponseDto response = service.getOrderDraft(requestDto);

        DraftTicketPriceResponseDto expectedResponse = createExpectedResponse(BigDecimal.valueOf(21.78));
        expectedResponse.setPassengerDtoList(
                Arrays.asList(
                        createExpectedPassenger(ticketsForAdult, BigDecimal.valueOf(15.73)),
                        createExpectedPassenger(ticketsForChild, BigDecimal.valueOf(6.05))
                ));

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expectedResponse);


        verify(taxRateApiAdapter).getVat();
        verify(ticketBasePriceApiAdapter).getBasePrice(route);

        verify(ticketDraftPriceCalculationService).getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(0)), eq(ADULT_TICKET_BASE_PRICE), eq(VAT));

        verify(ticketDraftPriceCalculationService).getAllPassengerTickets(
                eq(requestDto.getPassengerRequestDtoList().get(1)), eq(ADULT_TICKET_BASE_PRICE), eq(VAT));

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
                .vat(VAT)
                .totalOrderPrice(totalOrderPrice)
                .build();
    }

    private PassengerResponseDto createExpectedPassenger(List<TicketResponseDto> ticketResponseDtoList, BigDecimal totalOrderPrice) {
        return PassengerResponseDto.builder()
                .totalPassengerOrderPrice(totalOrderPrice)
                .ticketResponseDtoList(ticketResponseDtoList)
                .build();
    }

    private TicketResponseDto createExpectedTicketResponseDto(TicketType ticketType,
                                                              BigDecimal ticketPrice,
                                                              BigDecimal ticketVat) {
        return TicketResponseDto.builder()
                .ticketType(ticketType)
                .ticketPriceWithoutVat(ticketPrice)
                .vat(ticketVat)
                .ticketTotalPrice(ticketPrice.add(ticketVat))
                .build();
    }
}