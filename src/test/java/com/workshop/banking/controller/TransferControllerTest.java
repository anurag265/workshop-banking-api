package com.workshop.banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.banking.dto.TransferRequest;
import com.workshop.banking.dto.TransferResponse;
import com.workshop.banking.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransferControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private TransferService transferService;

    @BeforeEach
    void setUp() {
        transferService = new TransferService(null, null, null, null) {
            @Override
            public TransferResponse executeTransfer(TransferRequest request) {
                TransferResponse response = new TransferResponse();
                response.setReferenceId("test-reference");
                response.setStatus("COMPLETED");
                response.setFromAccount("FIS-1001-0001");
                response.setToAccount("FIS-1001-0002");
                response.setAmount(new BigDecimal("50.00"));
                response.setTimestamp(LocalDateTime.of(2026, 3, 15, 12, 0));
                return response;
            }
        };
        mockMvc = MockMvcBuilders.standaloneSetup(new TransferController(transferService)).build();
    }

    @Test
    void shouldReturnTransferResponse() throws Exception {
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal("50.00"));
        request.setCurrency("USD");
        request.setDescription("Controller test");

        mockMvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.referenceId").value("test-reference"))
            .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}
