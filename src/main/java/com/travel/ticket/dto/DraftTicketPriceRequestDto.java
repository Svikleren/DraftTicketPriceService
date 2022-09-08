package com.travel.ticket.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class DraftTicketPriceRequestDto {

    @NotBlank(message = "Route should be provided to calculate base ticket price")
    private String route;

    @Size(min = 1, message = "Please provide data about at least one passenger to proceed with draft ticket price calculation")
    @Valid
    private List<PassengerRequestDto> passengerRequestDtoList;

}
