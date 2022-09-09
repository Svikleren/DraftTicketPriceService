package com.travel.ticket.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstants {

    public static final BigDecimal ADULT_TICKET_BASE_PRICE = BigDecimal.TEN;
    public static final BigDecimal VAT = BigDecimal.valueOf(0.21);

}
