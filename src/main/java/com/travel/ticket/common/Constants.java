package com.travel.ticket.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final BigDecimal CHILD_TICKET_DISCOUNT = BigDecimal.valueOf(0.5);
    public static final BigDecimal LUGGAGE_TICKET_PRICE = BigDecimal.valueOf(0.3);
}
