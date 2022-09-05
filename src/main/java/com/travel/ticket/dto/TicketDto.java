package com.travel.ticket.dto;

import com.travel.ticket.TicketType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDto {

    private TicketType ticketType;
    private int luggageCount;

    private BigDecimal ticketBasePrice;
    private BigDecimal ticketPrice;

    private BigDecimal luggageTicketsTotalPrice;

}
