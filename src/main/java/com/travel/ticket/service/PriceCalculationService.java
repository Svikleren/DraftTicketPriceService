package com.travel.ticket.service;

import com.travel.ticket.TicketType;
import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.dto.PassengerDto;
import com.travel.ticket.dto.TicketDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.travel.ticket.constants.Constants.CHILD_TICKET_DISCOUNT;
import static com.travel.ticket.constants.Constants.LUGGAGE_TICKET_PRICE;

@Service
public class PriceCalculationService {

    public DraftTicketPriceResponseDto getAllTicketDraftPrice(DraftTicketPriceRequestDto request) {
        String route = request.getRoute();
        BigDecimal vat = getVat();

        List<TicketDto> ticketDtoList = getAllTicketPrices(request.getPassengerDtoList(), route, vat);

        BigDecimal totalPrice = getOrderTotalPrice(ticketDtoList);

        DraftTicketPriceResponseDto response = new DraftTicketPriceResponseDto();
        response.setRoute(route);
        response.setVat(vat);
        response.setTicketDtoList(ticketDtoList);
        response.setTotalOrderPrice(totalPrice);

        //todo builder pattern

        return response;
    }

    private List<TicketDto> getAllTicketPrices(List<PassengerDto> passengerDtoList, String route, BigDecimal vat) {
        return passengerDtoList.stream()
                .map(passengerDto -> getTicketPrice(passengerDto, route, vat))
                .collect(Collectors.toList());
    }

    private TicketDto getTicketPrice(PassengerDto passenger, String route, BigDecimal vat) {
        BigDecimal ticketBasePrice = getBasePrice(route);
        BigDecimal luggageCount = BigDecimal.valueOf(passenger.getLuggageCount());

        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketType(passenger.isChild() ? TicketType.CHILD : TicketType.ADULT);
        ticketDto.setTicketBasePrice(ticketBasePrice);
        ticketDto.setLuggageCount(passenger.getLuggageCount());

        BigDecimal passengerTicketPriceWithoutVat = passenger.isChild() ?
                ticketBasePrice.multiply(CHILD_TICKET_DISCOUNT)
                : ticketBasePrice;

        ticketDto.setTicketPrice(passengerTicketPriceWithoutVat.add(passengerTicketPriceWithoutVat.multiply(vat)));

        BigDecimal totalLuggageTicketsPriceWithoutVat = (ticketBasePrice.multiply(LUGGAGE_TICKET_PRICE).multiply(luggageCount));
        ticketDto.setLuggageTicketsTotalPrice(totalLuggageTicketsPriceWithoutVat.add(totalLuggageTicketsPriceWithoutVat.multiply(vat)));

        return ticketDto;
    }

    private BigDecimal getOrderTotalPrice(List<TicketDto> ticketDtoList) {
        return ticketDtoList.stream()
                .map(ticketDto -> ticketDto.getTicketPrice().add(ticketDto.getLuggageTicketsTotalPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getVat() {
        return BigDecimal.valueOf(0.21);
    } //todo

    private BigDecimal getBasePrice(String route) {
        return BigDecimal.TEN;
    } //todo

}
