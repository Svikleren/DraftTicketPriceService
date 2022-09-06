package com.travel.ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PassengerDto {

    private int luggageCount;
    private boolean isChild;
}
