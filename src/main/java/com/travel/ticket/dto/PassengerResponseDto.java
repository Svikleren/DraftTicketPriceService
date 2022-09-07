package com.travel.ticket.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PassengerResponseDto {

    private BigDecimal totalPassengerOrderPrice;
    private List<TicketResponseDto> ticketResponseDtoList;

}
