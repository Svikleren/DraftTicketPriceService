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

    @Valid
    @Size(min = 1, message = "Data about at least one passenger needed to proceed with draft ticket price calculation")
    private List<PassengerRequestDto> passengerRequestDtoList;

}
