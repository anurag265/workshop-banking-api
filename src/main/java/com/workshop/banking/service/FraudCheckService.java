package com.workshop.banking.service;

import com.workshop.banking.dto.TransferRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;

@Service
public class FraudCheckService {

    private static final Logger logger = LoggerFactory.getLogger(FraudCheckService.class);

    public FraudCheckResult reviewTransfer(TransferRequest request) {
        String description = request.getDescription() == null ? "" : request.getDescription().toLowerCase(Locale.ROOT);

        if (request.getAmount() != null && request.getAmount().compareTo(new BigDecimal("10000.00")) > 0) {
            logger.warn("Transfer flagged for review because amount exceeds threshold");
            return new FraudCheckResult(true, "High transfer amount", 50);
        }

        if (description.contains("urgent") || description.contains("wire immediately")) {
            logger.warn("Transfer flagged for review because description looks suspicious");
            return new FraudCheckResult(true, "Suspicious transfer description", 75);
        }

        return new FraudCheckResult(false, "No issues detected", 0);
    }
}
