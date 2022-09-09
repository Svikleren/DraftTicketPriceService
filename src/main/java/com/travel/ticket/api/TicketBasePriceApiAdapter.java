package com.travel.ticket.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketBasePriceApiAdapter {

    @Value("${baseprice.url}")
    private String basePriceUrl;

    private final RestTemplate restTemplate;

    public BigDecimal getBasePrice(String route) {
        try {
            log.debug(String.format("Calling external service to get ticket base price to %s", route));
            ResponseEntity<BigDecimal> response = restTemplate.getForEntity(basePriceUrl + route, BigDecimal.class);
            return response.getBody();
        } catch (RestClientException exception) {
            log.error(String.format("Error encountered while requesting ticket base price for destination %s", route));
            throw exception;
        }
    }
}
