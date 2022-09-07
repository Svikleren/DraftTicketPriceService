package com.travel.ticket.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DraftTicketPriceResponseDto {

    private String route;
    private BigDecimal vat;
    private BigDecimal totalOrderPrice;
    private List<PassengerResponseDto> passengerDtoList;

}
