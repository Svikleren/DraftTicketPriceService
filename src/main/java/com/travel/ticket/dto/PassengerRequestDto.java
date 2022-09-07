package com.travel.ticket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerRequestDto {

    private int luggageCount;
    private boolean isChild;
}
