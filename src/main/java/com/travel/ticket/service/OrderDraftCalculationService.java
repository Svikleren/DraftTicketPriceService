package com.travel.ticket.service;

import com.travel.ticket.api.TaxRateApiAdapter;
import com.travel.ticket.api.TicketBasePriceApiAdapter;
import com.travel.ticket.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderDraftCalculationService class:
 * - requests necessary information from external services (VAT and ticket base price)
 * - create draft ticket order response
 * - creates DraftTicketPriceResponseDto with information about each provided passenger
 * - calls TicketDraftPriceCalculationService to create each passenger tickets
 * - calculates total price for each passenger and for whole order
 */
@Service
@RequiredArgsConstructor
public class OrderDraftCalculationService {

    private final TicketBasePriceApiAdapter ticketBasePriceApiAdapter;
    private final TicketDraftPriceCalculationService ticketDraftPriceCalculationService;
    private final TaxRateApiAdapter taxRateApiAdapter;

    public DraftTicketPriceResponseDto getOrderDraft(DraftTicketPriceRequestDto request) {
        String route = request.getRoute();
        BigDecimal vat = taxRateApiAdapter.getVat();

        List<PassengerResponseDto> passengerResponseDtoList =
                createPassengersOrders(request.getPassengerRequestDtoList(), route, vat);
        BigDecimal totalPrice = getTotalPassengerOrderPrice(passengerResponseDtoList);

        return DraftTicketPriceResponseDto.builder()
                .route(route)
                .vat(vat)
                .passengerDtoList(passengerResponseDtoList)
                .totalOrderPrice(totalPrice)
                .build();
    }

    private List<PassengerResponseDto> createPassengersOrders(List<PassengerRequestDto> passengerRequestDtoList,
                                                              String route,
                                                              BigDecimal vat) {
        BigDecimal ticketBasePrice = ticketBasePriceApiAdapter.getBasePrice(route);
        return passengerRequestDtoList.stream()
                .map(passengerRequestDto -> createPassengerOrder(passengerRequestDto, ticketBasePrice, vat))
                .collect(Collectors.toList());
    }

    private PassengerResponseDto createPassengerOrder(PassengerRequestDto passengerRequestDto,
                                                      BigDecimal ticketBasePrice,
                                                      BigDecimal vat) {
        List<TicketResponseDto> ticketResponseDtoList =
                ticketDraftPriceCalculationService.getAllPassengerTickets(passengerRequestDto, ticketBasePrice, vat);

        return PassengerResponseDto.builder()
                .ticketResponseDtoList(ticketResponseDtoList)
                .totalPassengerOrderPrice(getTotalOrderPrice(ticketResponseDtoList))
                .build();
    }

    private BigDecimal getTotalOrderPrice(List<TicketResponseDto> ticketResponseDtoList) {
        return ticketResponseDtoList.stream()
                .map(TicketResponseDto::getTicketTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalPassengerOrderPrice(List<PassengerResponseDto> passengerResponseDtoList) {
        return passengerResponseDtoList.stream()
                .map(PassengerResponseDto::getTotalPassengerOrderPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
