package com.travel.ticket.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.travel.ticket.common.TestConstants.VAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaxRateApiAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TaxRateApiAdapter taxRateApiAdapter;

    @Captor
    private ArgumentCaptor<String> urlCaptor;

    @Test
    void getVat() {
        LocalDate today = LocalDate.now();

        when(restTemplate.getForEntity(anyString(), eq(BigDecimal.class)))
                .thenReturn(ResponseEntity.of(Optional.of(VAT)));

        BigDecimal vat = taxRateApiAdapter.getVat();

        assertThat(vat).isEqualTo(VAT);
        verify(restTemplate).getForEntity(urlCaptor.capture(), eq(BigDecimal.class));
        assertThat(urlCaptor.getValue()).contains(today.toString());
        verifyNoMoreInteractions(restTemplate);
    }
}