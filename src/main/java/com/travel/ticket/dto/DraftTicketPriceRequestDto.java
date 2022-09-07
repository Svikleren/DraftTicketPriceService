package com.travel.ticket.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class DraftTicketPriceRequestDto {

    @NotBlank(message = "Route should be provided to calculate base ticket price")
    private String route;
    private List<PassengerRequestDto> passengerRequestDtoList;

}
