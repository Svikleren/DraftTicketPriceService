package com.travel.ticket.dto;

import com.travel.ticket.common.TicketType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDto {

    private TicketType ticketType;
    private BigDecimal ticketPriceWithoutVat;
    private BigDecimal vat;
    private BigDecimal ticketTotalPrice;

}
