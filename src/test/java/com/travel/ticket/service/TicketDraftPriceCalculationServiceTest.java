package com.travel.ticket.service;

import com.travel.ticket.common.TicketType;
import com.travel.ticket.dto.PassengerRequestDto;
import com.travel.ticket.dto.TicketResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TicketDraftPriceCalculationServiceTest {

    private final TicketDraftPriceCalculationService ticketCalculationService = new TicketDraftPriceCalculationService();

    @Test
    void getTicketPrice_AdultAndLuggage() {
        BigDecimal adultTicketBasePrice = BigDecimal.TEN;
        BigDecimal luggageTicketBasePrice = adultTicketBasePrice.multiply(BigDecimal.valueOf(0.3));
        BigDecimal vat = BigDecimal.valueOf(0.21);

        List<TicketResponseDto> ticketResponseDtoList =
                ticketCalculationService.getAllPassengerTickets(createPassengerRequest(false), adultTicketBasePrice, vat);

        List<TicketResponseDto> expectedTicketsList = Arrays.asList(
                createExpectedTicketResponseDto(TicketType.ADULT, adultTicketBasePrice, adultTicketBasePrice.multiply(vat)),
                createExpectedTicketResponseDto(TicketType.LUGGAGE, luggageTicketBasePrice, luggageTicketBasePrice.multiply(vat))
        );

        assertThat(ticketResponseDtoList).usingRecursiveComparison()
                .isEqualTo(expectedTicketsList);
    }

    @Test
    void getTicketPrice_ChildAndLuggage() {
        BigDecimal adultTicketBasePrice = BigDecimal.TEN;
        BigDecimal childTicketBasePrice = adultTicketBasePrice.multiply(BigDecimal.valueOf(0.5));
        BigDecimal luggageTicketBasePrice = adultTicketBasePrice.multiply(BigDecimal.valueOf(0.3));
        BigDecimal vat = BigDecimal.valueOf(0.21);

        List<TicketResponseDto> ticketResponseDtoList =
                ticketCalculationService.getAllPassengerTickets(createPassengerRequest(true), adultTicketBasePrice, vat);

        List<TicketResponseDto> expectedTicketsList = Arrays.asList(
                createExpectedTicketResponseDto(TicketType.CHILD, childTicketBasePrice, childTicketBasePrice.multiply(vat)),
                createExpectedTicketResponseDto(TicketType.LUGGAGE, luggageTicketBasePrice, luggageTicketBasePrice.multiply(vat))
        );

        assertThat(ticketResponseDtoList).usingRecursiveComparison()
                .isEqualTo(expectedTicketsList);
    }

    private PassengerRequestDto createPassengerRequest(boolean isChild) {
        return PassengerRequestDto.builder()
                .luggageCount(1)
                .isChild(isChild)
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