package com.workshop.banking.controller;

import com.workshop.banking.dto.TransferRequest;
import com.workshop.banking.dto.TransferResponse;
import com.workshop.banking.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public TransferResponse createTransfer(@RequestBody TransferRequest request) {
        return transferService.executeTransfer(request);
    }
}
