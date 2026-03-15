package com.workshop.banking.service;

import com.workshop.banking.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AuditService {

    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);

    public void logSuccess(Account source, Account destination, BigDecimal amount) {
        logger.info("Transfer audit timestamp={} source={} destination={} amount={} result=SUCCESS",
            LocalDateTime.now(), source.getAccountNumber(), destination.getAccountNumber(), amount);
    }

    public void logFlagged(Account source, Account destination, BigDecimal amount, String reason) {
        logger.warn("Transfer audit timestamp={} source={} destination={} amount={} result=FLAGGED reason={}",
            LocalDateTime.now(), source.getAccountNumber(), destination.getAccountNumber(), amount, reason);
    }

    public void logFailure(Long sourceAccountId, Long destinationAccountId, BigDecimal amount, String reason) {
        logger.error("Transfer audit timestamp={} source={} destination={} amount={} result=FAILED reason={}",
            LocalDateTime.now(), sourceAccountId, destinationAccountId, amount, reason);
    }
}
