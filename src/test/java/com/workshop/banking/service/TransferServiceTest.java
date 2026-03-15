package com.workshop.banking.service;

import com.workshop.banking.dto.TransferRequest;
import com.workshop.banking.exception.AccountNotFoundException;
import com.workshop.banking.exception.InvalidTransferException;
import com.workshop.banking.model.Account;
import com.workshop.banking.repository.AccountRepository;
import com.workshop.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransferServiceTest {

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    private TransferService transferService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        transferService = new TransferService(accountRepository, transactionRepository, new FraudCheckService(), new AuditService());
    }

    @Test
    void shouldRejectZeroAmountTransfer() {
        TransferRequest request = request(1L, 2L, "0.00");

        InvalidTransferException exception = assertThrows(InvalidTransferException.class,
            () -> transferService.executeTransfer(request));

        assertEquals("Transfer amount must be greater than zero", exception.getMessage());
        verify(accountRepository, never()).findById(any());
    }

    @Test
    void shouldRejectWhenSourceAccountIsMissing() {
        TransferRequest request = request(99L, 2L, "25.00");
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class,
            () -> transferService.executeTransfer(request));

        assertEquals("Source account not found", exception.getMessage());
    }

    @Test
    void shouldRejectSelfTransfer() {
        TransferRequest request = request(1L, 1L, "25.00");
        Account sameAccount = activeAccount(1L, "FIS-1001-0001", "25000.00");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(sameAccount));

        InvalidTransferException exception = assertThrows(InvalidTransferException.class,
            () -> transferService.executeTransfer(request));

        assertEquals("Cannot transfer to the same account", exception.getMessage());
    }

    @Test
    void shouldCompleteBasicTransfer() {
        TransferRequest request = request(1L, 2L, "100.00");
        Account source = activeAccount(1L, "FIS-1001-0001", "25000.00");
        Account destination = activeAccount(2L, "FIS-1001-0002", "15500.00");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(source));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(destination));

        var response = transferService.executeTransfer(request);

        assertEquals("COMPLETED", response.getStatus());
        assertEquals(new BigDecimal("24900.00"), source.getBalance());
        assertEquals(new BigDecimal("15600.00"), destination.getBalance());
        verify(transactionRepository).save(any());
    }

    private TransferRequest request(Long fromAccountId, Long toAccountId, String amount) {
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(fromAccountId);
        request.setToAccountId(toAccountId);
        request.setAmount(new BigDecimal(amount));
        request.setCurrency("USD");
        request.setDescription("Test transfer");
        return request;
    }

    private Account activeAccount(Long id, String accountNumber, String balance) {
        Account account = new Account(accountNumber, "Test Holder", new BigDecimal(balance), "USD", "ACTIVE");
        try {
            var field = Account.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(account, id);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException(exception);
        }
        return account;
    }
}
