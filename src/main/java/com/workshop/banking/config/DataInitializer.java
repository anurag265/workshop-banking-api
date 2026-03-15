package com.workshop.banking.config;

import com.workshop.banking.model.Account;
import com.workshop.banking.model.Transaction;
import com.workshop.banking.model.TransactionStatus;
import com.workshop.banking.repository.AccountRepository;
import com.workshop.banking.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSeedData(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        return args -> {
            if (accountRepository.count() > 0 || transactionRepository.count() > 0) {
                return;
            }

            List<Account> accounts = accountRepository.saveAll(List.of(
                new Account("FIS-1001-0001", "Alice Johnson", new BigDecimal("25000.00"), "USD", "ACTIVE"),
                new Account("FIS-1001-0002", "Bob Smith", new BigDecimal("15500.00"), "USD", "ACTIVE"),
                new Account("FIS-1001-0003", "Charlie Davis", new BigDecimal("42000.00"), "USD", "ACTIVE"),
                new Account("FIS-1001-0004", "Diana Lee", new BigDecimal("8750.00"), "USD", "ACTIVE"),
                new Account("FIS-1001-0005", "Frozen Corp", new BigDecimal("100000.00"), "USD", "FROZEN"),
                new Account("FIS-1001-0006", "Closed Account LLC", new BigDecimal("0.00"), "USD", "CLOSED")
            ));

            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

            transactionRepository.saveAll(List.of(
                historicalTransaction(accounts.get(0), accounts.get(1), "250.00", "Utility payment", TransactionStatus.COMPLETED, now.minusDays(30)),
                historicalTransaction(accounts.get(1), accounts.get(0), "175.50", "Shared expenses", TransactionStatus.COMPLETED, now.minusDays(27)),
                historicalTransaction(accounts.get(2), accounts.get(3), "1250.00", "Monthly allowance", TransactionStatus.COMPLETED, now.minusDays(24)),
                historicalTransaction(accounts.get(3), accounts.get(0), "89.99", "Subscription reimbursement", TransactionStatus.COMPLETED, now.minusDays(21)),
                historicalTransaction(accounts.get(0), accounts.get(2), "4300.00", "Consulting invoice", TransactionStatus.COMPLETED, now.minusDays(19)),
                historicalTransaction(accounts.get(2), accounts.get(1), "640.25", "Travel settlement", TransactionStatus.COMPLETED, now.minusDays(17)),
                historicalTransaction(accounts.get(1), accounts.get(3), "320.00", "Team lunch", TransactionStatus.COMPLETED, now.minusDays(14)),
                historicalTransaction(accounts.get(3), accounts.get(2), "1500.00", "Equipment refund", TransactionStatus.COMPLETED, now.minusDays(10)),
                historicalTransaction(accounts.get(0), accounts.get(3), "999.99", "Quarterly services", TransactionStatus.COMPLETED, now.minusDays(8)),
                historicalTransaction(accounts.get(2), accounts.get(0), "710.40", "Insurance adjustment", TransactionStatus.COMPLETED, now.minusDays(6)),
                historicalTransaction(accounts.get(1), accounts.get(2), "75.00", "Coffee budget", TransactionStatus.COMPLETED, now.minusDays(3)),
                historicalTransaction(accounts.get(3), accounts.get(1), "210.15", "Office supplies", TransactionStatus.FAILED, now.minusDays(1))
            ));
        };
    }

    private Transaction historicalTransaction(Account fromAccount,
                                              Account toAccount,
                                              String amount,
                                              String description,
                                              TransactionStatus status,
                                              LocalDateTime createdAt) {
        LocalDateTime completedAt = status == TransactionStatus.COMPLETED
            ? createdAt.plusMinutes(5)
            : null;

        return new Transaction(
            UUID.randomUUID().toString(),
            fromAccount.getId(),
            toAccount.getId(),
            new BigDecimal(amount),
            "USD",
            description,
            status,
            createdAt,
            completedAt
        );
    }
}
