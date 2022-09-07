package com.travel.ticket.dto;

import com.travel.ticket.common.TicketType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TicketResponseDto {

    private TicketType ticketType;
    private BigDecimal ticketPriceWithoutVat;
    private BigDecimal vat;
    private BigDecimal ticketTotalPrice;

}
