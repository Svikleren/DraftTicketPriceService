package com.travel.ticket.service;

import com.travel.ticket.common.TicketType;
import com.travel.ticket.dto.PassengerRequestDto;
import com.travel.ticket.dto.TicketResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.travel.ticket.common.TestConstants.ADULT_TICKET_BASE_PRICE;
import static com.travel.ticket.common.TestConstants.VAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TicketDraftPriceCalculationServiceTest {

    private final TicketDraftPriceCalculationService ticketCalculationService = new TicketDraftPriceCalculationService();

    @Test
    void getTicketPrice_AdultAndLuggage() {
        BigDecimal adultTicketBasePrice = ADULT_TICKET_BASE_PRICE;
        BigDecimal luggageTicketBasePrice = adultTicketBasePrice.multiply(BigDecimal.valueOf(0.3));

        List<TicketResponseDto> ticketResponseDtoList =
                ticketCalculationService.getAllPassengerTickets(createPassengerRequest(false), adultTicketBasePrice, VAT);

        List<TicketResponseDto> expectedTicketsList = Arrays.asList(
                createExpectedTicketResponseDto(TicketType.ADULT, adultTicketBasePrice, adultTicketBasePrice.multiply(VAT)),
                createExpectedTicketResponseDto(TicketType.LUGGAGE, luggageTicketBasePrice, luggageTicketBasePrice.multiply(VAT))
        );

        assertThat(ticketResponseDtoList).usingRecursiveComparison()
                .isEqualTo(expectedTicketsList);
    }

    @Test
    void getTicketPrice_ChildAndLuggage() {
        BigDecimal adultTicketBasePrice = ADULT_TICKET_BASE_PRICE;
        BigDecimal childTicketBasePrice = adultTicketBasePrice.multiply(BigDecimal.valueOf(0.5));
        BigDecimal luggageTicketBasePrice = adultTicketBasePrice.multiply(BigDecimal.valueOf(0.3));

        List<TicketResponseDto> ticketResponseDtoList =
                ticketCalculationService.getAllPassengerTickets(createPassengerRequest(true), adultTicketBasePrice, VAT);

        List<TicketResponseDto> expectedTicketsList = Arrays.asList(
                createExpectedTicketResponseDto(TicketType.CHILD, childTicketBasePrice, childTicketBasePrice.multiply(VAT)),
                createExpectedTicketResponseDto(TicketType.LUGGAGE, luggageTicketBasePrice, luggageTicketBasePrice.multiply(VAT))
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