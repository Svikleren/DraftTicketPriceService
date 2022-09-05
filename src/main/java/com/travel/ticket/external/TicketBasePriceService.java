package com.travel.ticket.external;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TicketBasePriceService {

    public BigDecimal getBasePrice(String route) {
        return BigDecimal.TEN;
    } //todo
}
