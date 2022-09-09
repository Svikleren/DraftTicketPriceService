package com.travel.ticket.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxRateApiAdapter {

    @Value("${vat.url}")
    String vatUrl;

    private final RestTemplate restTemplate;

    public BigDecimal getVat() {
        LocalDate today = LocalDate.now();
        try {
            log.debug("Calling external service to get current VAT");
            ResponseEntity<BigDecimal> response = restTemplate.getForEntity(vatUrl + today, BigDecimal.class);
            return response.getBody();
        } catch (RestClientException exception) {
            log.error("Error encountered while requesting current VAT information");
            throw exception;
        }
    }

}
