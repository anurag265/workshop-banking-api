package com.workshop.banking.service;

import com.workshop.banking.dto.TransferRequest;
import com.workshop.banking.dto.TransferResponse;
import com.workshop.banking.exception.AccountNotFoundException;
import com.workshop.banking.exception.InsufficientFundsException;
import com.workshop.banking.exception.InvalidTransferException;
import com.workshop.banking.model.Account;
import com.workshop.banking.model.Transaction;
import com.workshop.banking.model.TransactionStatus;
import com.workshop.banking.repository.AccountRepository;
import com.workshop.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final FraudCheckService fraudCheckService;
    private final AuditService auditService;

    public TransferService(AccountRepository accountRepository,
                           TransactionRepository transactionRepository,
                           FraudCheckService fraudCheckService,
                           AuditService auditService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.fraudCheckService = fraudCheckService;
        this.auditService = auditService;
    }

    @Transactional
    public TransferResponse executeTransfer(TransferRequest request) {
        BigDecimal amount = request.getAmount();

        // TODO: fix this later when the rules stop changing every week.
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            auditService.logFailure(request.getFromAccountId(), request.getToAccountId(), amount, "Transfer amount must be greater than zero");
            throw new InvalidTransferException("Transfer amount must be greater than zero");
        }
        if (amount.compareTo(new BigDecimal("1.00")) < 0) {
            auditService.logFailure(request.getFromAccountId(), request.getToAccountId(), amount, "Minimum transfer amount is $1.00");
            throw new InvalidTransferException("Minimum transfer amount is $1.00");
        }
        if (amount.compareTo(new BigDecimal("50000.00")) > 0) {
            auditService.logFailure(request.getFromAccountId(), request.getToAccountId(), amount, "Maximum single transfer is $50,000.00");
            throw new InvalidTransferException("Maximum single transfer is $50,000.00");
        }

        Optional<Account> sourceLookup = accountRepository.findById(request.getFromAccountId());
        if (sourceLookup.isEmpty()) {
            auditService.logFailure(request.getFromAccountId(), request.getToAccountId(), amount, "Source account not found");
            throw new AccountNotFoundException("Source account not found");
        }

        Optional<Account> destinationLookup = accountRepository.findById(request.getToAccountId());
        if (destinationLookup.isEmpty()) {
            auditService.logFailure(request.getFromAccountId(), request.getToAccountId(), amount, "Destination account not found");
            throw new AccountNotFoundException("Destination account not found");
        }

        Account source = sourceLookup.get();
        Account destination = destinationLookup.get();

        if (source.getId().equals(destination.getId())) {
            auditService.logFailure(source.getId(), destination.getId(), amount, "Cannot transfer to the same account");
            throw new InvalidTransferException("Cannot transfer to the same account");
        }
        if (!"ACTIVE".equalsIgnoreCase(source.getStatus())) {
            auditService.logFailure(source.getId(), destination.getId(), amount, "Source account is not active");
            throw new InvalidTransferException("Source account is not active");
        }
        if (!"ACTIVE".equalsIgnoreCase(destination.getStatus())) {
            auditService.logFailure(source.getId(), destination.getId(), amount, "Destination account is not active");
            throw new InvalidTransferException("Destination account is not active");
        }

        FraudCheckResult fraudCheckResult = fraudCheckService.reviewTransfer(request);
        if (source.getBalance().compareTo(amount) < 0) {
            auditService.logFailure(source.getId(), destination.getId(), amount, "Insufficient funds in source account");
            throw new InsufficientFundsException("Insufficient funds in source account");
        }

        // Still doing this inline because it is easier to debug in one place during workshops.
        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));
        accountRepository.save(source);
        accountRepository.save(destination);

        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction(
            UUID.randomUUID().toString(),
            source.getId(),
            destination.getId(),
            amount,
            request.getCurrency() == null || request.getCurrency().isBlank() ? "USD" : request.getCurrency(),
            request.getDescription(),
            TransactionStatus.COMPLETED,
            timestamp,
            timestamp
        );
        transactionRepository.save(transaction);

        if (fraudCheckResult.isFlagged()) {
            auditService.logFlagged(source, destination, amount, fraudCheckResult.getReason());
        } else {
            auditService.logSuccess(source, destination, amount);
        }

        TransferResponse response = new TransferResponse();
        response.setReferenceId(transaction.getReferenceId());
        response.setStatus(transaction.getStatus().name());
        response.setFromAccount(source.getAccountNumber());
        response.setToAccount(destination.getAccountNumber());
        response.setAmount(transaction.getAmount());
        response.setTimestamp(timestamp);
        return response;
    }
}
