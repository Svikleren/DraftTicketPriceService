package com.travel.ticket.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DraftTicketPriceResponseDto {

    private String route;
    private BigDecimal vat;
    private BigDecimal totalOrderPrice;
    private List<TicketDto> ticketDtoList;

}
