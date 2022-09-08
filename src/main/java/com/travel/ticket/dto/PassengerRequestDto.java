package com.travel.ticket.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@Builder
public class PassengerRequestDto {

    @Min(value = 0, message = "Luggage count can't be negative value")
    private int luggageCount;
    private boolean isChild;
}
