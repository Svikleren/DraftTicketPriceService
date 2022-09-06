package com.travel.ticket.service;

import com.travel.ticket.common.TicketType;
import com.travel.ticket.dto.*;
import com.travel.ticket.external.TaxRateService;
import com.travel.ticket.external.TicketBasePriceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.travel.ticket.common.Constants.CHILD_TICKET_DISCOUNT;
import static com.travel.ticket.common.Constants.LUGGAGE_TICKET_PRICE;

@Service
@AllArgsConstructor
public class DraftTicketPriceCalculationService {

    private final TicketBasePriceService ticketBasePriceService; //todo
    private final TaxRateService taxRateService; //todo

    public DraftTicketPriceResponseDto getAllTicketDraftPrice(DraftTicketPriceRequestDto request) {
        String route = request.getRoute();
        BigDecimal vat = taxRateService.getVat();

        List<PassengerResponseDto> passengerResponseDtoList = createPassengerOrders(request.getPassengerDtoList(), route, vat);
        BigDecimal totalPrice = getOrderTotalPrice(passengerResponseDtoList);

        DraftTicketPriceResponseDto response = new DraftTicketPriceResponseDto();
        response.setRoute(route);
        response.setVat(vat);
        response.setPassengerDtoList(passengerResponseDtoList);
        response.setTotalOrderPrice(totalPrice);

        //todo builder pattern

        return response;
    }

    private List<PassengerResponseDto> createPassengerOrders(List<PassengerDto> passengerDtoList, String route, BigDecimal vat) {
        return passengerDtoList.stream()
                .map(passengerDto -> createPassengerOrder(passengerDto, route, vat))
                .collect(Collectors.toList());
    }

    private PassengerResponseDto createPassengerOrder(PassengerDto passengerDto, String route, BigDecimal vat) {
        List<TicketDto> ticketDtoList = getTicketPrice(passengerDto, route, vat);
        BigDecimal totalPrice = getPassengerOrderTotalPrice(ticketDtoList);

        PassengerResponseDto passengerResponseDto = new PassengerResponseDto();
        passengerResponseDto.setTicketDtoList(ticketDtoList);
        passengerResponseDto.setTotalPassengerOrderPrice(totalPrice);

        return passengerResponseDto;
    }

    private List<TicketDto> getTicketPrice(PassengerDto passenger, String route, BigDecimal vat) {
        BigDecimal ticketBasePrice = ticketBasePriceService.getBasePrice(route);
        List<TicketDto> ticketDtoList = new ArrayList<>();
        ticketDtoList.add(createPassengerTicket(ticketBasePrice, vat, passenger.isChild()));

        for (int i = 1; i <= passenger.getLuggageCount(); i++) {
            ticketDtoList.add(createLuggageTicket(ticketBasePrice, vat));
        }

        return ticketDtoList;
    }

    private TicketDto createPassengerTicket(BigDecimal ticketBasePrice, BigDecimal vat, boolean isChild) {
        BigDecimal ticketPriceWithoutVat = isChild ?
                ticketBasePrice.multiply(CHILD_TICKET_DISCOUNT)
                : ticketBasePrice;
        BigDecimal ticketVat = ticketPriceWithoutVat.multiply(vat);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketType(isChild ? TicketType.CHILD : TicketType.ADULT);
        ticketDto.setTicketPriceWithoutVat(ticketPriceWithoutVat);
        ticketDto.setVat(ticketVat);
        ticketDto.setTicketTotalPrice(ticketPriceWithoutVat.add(ticketVat));

        return ticketDto;
    }

    private TicketDto createLuggageTicket(BigDecimal ticketBasePrice, BigDecimal vat) {
        BigDecimal ticketPriceWithoutVat = ticketBasePrice.multiply(LUGGAGE_TICKET_PRICE);
        BigDecimal ticketVat = ticketPriceWithoutVat.multiply(vat);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketType(TicketType.LUGGAGE);
        ticketDto.setTicketPriceWithoutVat(ticketPriceWithoutVat);
        ticketDto.setVat(ticketVat);
        ticketDto.setTicketTotalPrice(ticketPriceWithoutVat.add(ticketVat));

        return ticketDto;
    }

    private BigDecimal getOrderTotalPrice(List<PassengerResponseDto> passengerResponseDtoList) {
        return passengerResponseDtoList.stream()
                .map(PassengerResponseDto::getTotalPassengerOrderPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getPassengerOrderTotalPrice(List<TicketDto> ticketDtoList) {
        return ticketDtoList.stream()
                .map(TicketDto::getTicketTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
