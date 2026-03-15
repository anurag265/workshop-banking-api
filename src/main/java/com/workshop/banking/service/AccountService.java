package com.workshop.banking.service;

import com.workshop.banking.dto.AccountDTO;
import com.workshop.banking.exception.AccountNotFoundException;
import com.workshop.banking.model.Account;
import com.workshop.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException("Account not found for id " + id));
        return toDto(account);
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
            .stream()
            .map(this::toDto)
            .toList();
    }

    public List<AccountDTO> searchAccountsByHolder(String holder) {
        return accountRepository.findByHolderNameContainingIgnoreCase(holder)
            .stream()
            .map(this::toDto)
            .toList();
    }

    private AccountDTO toDto(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setHolderName(account.getHolderName());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setStatus(account.getStatus());
        dto.setCreatedAt(account.getCreatedAt());
        return dto;
    }
}
