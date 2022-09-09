package com.travel.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.ticket.dto.DraftTicketPriceRequestDto;
import com.travel.ticket.dto.DraftTicketPriceResponseDto;
import com.travel.ticket.dto.PassengerRequestDto;
import com.travel.ticket.service.OrderDraftCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DraftTicketPriceController.class)
class DraftTicketPriceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderDraftCalculationService orderDraftCalculationService;

    private final String route = "Vilnius";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getDraftTicketPrice() throws Exception {
        when(orderDraftCalculationService.getOrderDraft(any())).thenReturn(createExpectedResponse());

        mvc.perform(post("/draft-ticket-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDraftTicketPriceRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(createExpectedResponse())))
                .andDo(print());
    }

    private DraftTicketPriceRequestDto createDraftTicketPriceRequest() {
        PassengerRequestDto passengerRequestDtoAdult = PassengerRequestDto.builder()
                .luggageCount(1)
                .isChild(false)
                .build();
        PassengerRequestDto passengerRequestDtoChild = PassengerRequestDto.builder()
                .luggageCount(0)
                .isChild(true)
                .build();

        List<PassengerRequestDto> passengerRequestDtoList = new ArrayList<>();
        passengerRequestDtoList.add(passengerRequestDtoAdult);
        passengerRequestDtoList.add(passengerRequestDtoChild);

        return DraftTicketPriceRequestDto.builder()
                .route(route)
                .passengerRequestDtoList(passengerRequestDtoList)
                .build();

    }

    private DraftTicketPriceResponseDto createExpectedResponse() {
        return DraftTicketPriceResponseDto.builder()
                .route(route)
                .vat(BigDecimal.valueOf(0.21))
                .totalOrderPrice(BigDecimal.TEN)
                .build();
    }
}