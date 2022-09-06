package com.travel.ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DraftTicketPriceRequestDto {

    @NonNull
    private String route;
    private List<PassengerDto> passengerDtoList = new ArrayList<>();

}
