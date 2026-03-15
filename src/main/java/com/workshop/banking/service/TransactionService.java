package com.workshop.banking.service;

import com.workshop.banking.dto.TransactionDTO;
import com.workshop.banking.exception.TransactionNotFoundException;
import com.workshop.banking.model.Transaction;
import com.workshop.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException("Transaction not found for id " + id));
        return toDto(transaction);
    }

    public List<TransactionDTO> getTransactionsForAccount(Long accountId, LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findAll()
            .stream()
            .filter(transaction -> transaction.getFromAccountId().equals(accountId) || transaction.getToAccountId().equals(accountId))
            .filter(transaction -> from == null || !transaction.getCreatedAt().isBefore(from))
            .filter(transaction -> to == null || !transaction.getCreatedAt().isAfter(to))
            .map(this::toDto)
            .toList();
    }

    public TransactionDTO toDto(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setReferenceId(transaction.getReferenceId());
        dto.setFromAccountId(transaction.getFromAccountId());
        dto.setToAccountId(transaction.getToAccountId());
        dto.setAmount(transaction.getAmount());
        dto.setCurrency(transaction.getCurrency());
        dto.setDescription(transaction.getDescription());
        dto.setStatus(transaction.getStatus().name());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setCompletedAt(transaction.getCompletedAt());
        return dto;
    }
}
