package com.travel.ticket.external;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrentVatService {

    public BigDecimal getVat() {
        return BigDecimal.valueOf(0.21);
    }
}
