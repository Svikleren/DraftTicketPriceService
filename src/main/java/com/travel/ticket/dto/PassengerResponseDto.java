package com.travel.ticket.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PassengerResponseDto {

    private BigDecimal totalPassengerOrderPrice;
    private List<TicketDto> ticketDtoList = new ArrayList<>();
}
