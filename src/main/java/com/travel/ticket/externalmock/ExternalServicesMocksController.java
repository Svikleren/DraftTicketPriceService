package com.travel.ticket.externalmock;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Controller to mock and simulate external services behavior. Path variables 'route' and 'date' are
 * required in real world, but here presents unused only to implement correct service call from main API
 */
@RestController
public class ExternalServicesMocksController {

    @GetMapping({"/get-base-ticket-price/{route}"})
    public BigDecimal getBaseTicketPrice(
            @PathVariable String route) {
        return BigDecimal.TEN;
    }

    @GetMapping({"/get-current-vat/{date}"})
    public BigDecimal getCurrentVat(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return BigDecimal.valueOf(0.21);
    }
}
