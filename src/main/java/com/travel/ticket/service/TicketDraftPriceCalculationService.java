package com.travel.ticket.service;

import com.travel.ticket.common.TicketType;
import com.travel.ticket.dto.PassengerRequestDto;
import com.travel.ticket.dto.TicketResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.travel.ticket.common.Constants.CHILD_TICKET_DISCOUNT;
import static com.travel.ticket.common.Constants.LUGGAGE_TICKET_PRICE;

@Service
public class TicketDraftPriceCalculationService {

    public List<TicketResponseDto> getTicketPrice(PassengerRequestDto passenger, BigDecimal ticketBasePrice, BigDecimal vat) {
        int luggageCount = passenger.getLuggageCount();

        List<TicketResponseDto> ticketResponseDtoList = new ArrayList<>();
        ticketResponseDtoList.add(createPassengerTicket(ticketBasePrice, vat, passenger.isChild()));

        if (luggageCount > 0) {
            ticketResponseDtoList.addAll(createLuggageTickets(ticketBasePrice, vat, luggageCount));
        }

        return ticketResponseDtoList;
    }

    private TicketResponseDto createPassengerTicket(BigDecimal ticketBasePrice, BigDecimal vat, boolean isChild) {
        BigDecimal ticketPriceWithoutVat = isChild ?
                ticketBasePrice.multiply(CHILD_TICKET_DISCOUNT)
                : ticketBasePrice;

        return createTicket(ticketPriceWithoutVat, isChild ? TicketType.CHILD : TicketType.ADULT, vat);
    }

    private List<TicketResponseDto> createLuggageTickets(BigDecimal ticketBasePrice, BigDecimal vat, int luggageCount) {
        BigDecimal ticketPriceWithoutVat = ticketBasePrice.multiply(LUGGAGE_TICKET_PRICE);

        List<TicketResponseDto> luggageTickets = new ArrayList<>();
        IntStream.rangeClosed(1, luggageCount)
                .forEach(i -> luggageTickets.add(createTicket(ticketPriceWithoutVat, TicketType.LUGGAGE, vat)));

        return luggageTickets;
    }

    private TicketResponseDto createTicket(BigDecimal ticketPriceWithoutVat, TicketType ticketType, BigDecimal vat) {
        BigDecimal ticketVat = ticketPriceWithoutVat.multiply(vat);

        return TicketResponseDto.builder()
                .ticketType(ticketType)
                .ticketPriceWithoutVat(ticketPriceWithoutVat)
                .vat(ticketVat)
                .ticketTotalPrice(ticketPriceWithoutVat.add(ticketVat))
                .build();
    }
}
