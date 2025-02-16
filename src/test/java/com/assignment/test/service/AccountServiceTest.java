package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.*;
import com.assignment.test.dto.request.DepositRequest;
import com.assignment.test.dto.request.WithdrawalRequest;
import com.assignment.test.repository.AccountRepository;
import com.assignment.test.repository.databaseclient.AccountDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountDataRepository accountDataRepository;

    private final String userId = "user123";
    private final String accountId = "acc123";
    private final String accountNumber = "123-456-7899";

    @BeforeEach
    public void setup() {
        AccountDTO mockAcc = new AccountDTO(accountId, userId, "saving-account", accountNumber, "300.00", "Test1", "0", "c001", "41");
        Mockito.lenient().doReturn(Flux.fromIterable(List.of(mockAcc)))
                .when(accountRepository).findAccountInfo(userId);
    }

    @Test
    public void getAccountInfo_pass() {
        StepVerifier.create(accountService.getAccountInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equalsIgnoreCase(Constants.STATUS.SUCCESS)
                                && !res.getInfo().isEmpty()
                                && res.getInfo().get(0).getAccountNumber().equalsIgnoreCase(accountNumber)
                ).verifyComplete();
    }

    @Test
    void getAccountInfo_Fail() {
        Mockito.doReturn(Flux.error(new RuntimeException("Database Error!")))
                .when(accountRepository).findAccountInfo(userId);

        StepVerifier.create(accountService.getAccountInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.ERROR) &&
                                res.getMessage().equals("Database Error!") &&
                                res.getInfo().isEmpty()
                )
                .verifyComplete();
    }

    @Test
    void depositAccount_pass() {
        DepositDetailDTO depositDetail = new DepositDetailDTO(accountId, accountNumber, 500.0);
        DepositRequest request = new DepositRequest(userId, List.of(depositDetail));

        when(accountRepository.findAccountBLInfo(userId, accountId))
                .thenReturn(Flux.fromIterable(List.of(new AccountBLDTO(accountId, userId, 1000.0, accountNumber))));

        when(accountDataRepository.updateAccountBL(any()))
                .thenReturn(Mono.just(1L));

        StepVerifier.create(accountService.depositAccount(request))
                .expectNextMatches(res ->
                        res.getUserId().equals(userId) &&
                                res.getDepositDetail().get(0).getData().getAmount() == 500.0
                )
                .verifyComplete();
    }

    @Test
    void depositAccount_Fail() {
        DepositDetailDTO depositDetail = new DepositDetailDTO(accountId, accountNumber, 500.0);
        DepositRequest request = new DepositRequest(userId, List.of(depositDetail));

        when(accountRepository.findAccountBLInfo(userId, accountId))
                .thenReturn(Flux.fromIterable(Collections.emptyList()));

        StepVerifier.create(accountService.depositAccount(request))
                .expectNextMatches(res ->
                        res.getUserId().equals(userId) &&
                                res.getDepositDetail().get(0).getData().getAmount() == 500.0 &&
                                res.getDepositDetail().get(0).getData().getAccountId().equals(accountId)
                )
                .verifyComplete();
    }

    @Test
    void withdrawalAccount_pass() {
        WithdrawalDTO withdrawalDetail = new WithdrawalDTO(accountNumber, 300.0);
        WithdrawalRequest request = new WithdrawalRequest(userId, accountId, List.of(withdrawalDetail));

        when(accountRepository.findAccountBLInfo(userId, accountId))
                .thenReturn(Flux.fromIterable(List.of(new AccountBLDTO(accountId, userId, 1000.0, accountNumber))));

        when(accountRepository.findByAccountNumber(userId, accountNumber))
                .thenReturn(Flux.fromIterable(List.of(new AccountBLDTO(accountId, userId, 500.0, accountNumber))));

        when(accountDataRepository.updateAccountBL(any()))
                .thenReturn(Mono.just(1L));

        StepVerifier.create(accountService.withdrawalAccount(request))
                .expectNextMatches(res ->
                        res.getUserId().equals(userId) &&
                                res.getWithdrawalDetail().get(0).getData().getAmount() == 800.0
                )
                .verifyComplete();
    }

    @Test
    void withdrawalAccount_Fail() {
        WithdrawalDTO withdrawalDetail = new WithdrawalDTO(accountNumber, 1200.0); // ขอถอนเกินยอด
        WithdrawalRequest request = new WithdrawalRequest(userId, accountId, List.of(withdrawalDetail));

        when(accountRepository.findAccountBLInfo(userId, accountId))
                .thenReturn(Flux.fromIterable(List.of(new AccountBLDTO(accountId, userId, 1000.0, accountNumber))));

        when(accountRepository.findByAccountNumber(userId, accountNumber))
                .thenReturn(Flux.fromIterable(List.of(new AccountBLDTO(accountId, userId, 500.0, accountNumber))));

        StepVerifier.create(accountService.withdrawalAccount(request))
                .expectNextMatches(res ->
                        res.getUserId().equals(userId) &&
                                res.getWithdrawalDetail().get(0).getData().getAmount() == 1200.0 &&
                                res.getWithdrawalDetail().get(0).getStatus().equals(Constants.STATUS.ERROR)
                )
                .verifyComplete();
    }

    @Test
    void withdrawalAccount_Failure_AccountNotFound() {
        WithdrawalDTO withdrawalDetail = new WithdrawalDTO(accountNumber, 300.0);
        WithdrawalRequest request = new WithdrawalRequest(userId, accountId, List.of(withdrawalDetail));

        when(accountRepository.findAccountBLInfo(userId, accountId))
                .thenReturn(Flux.fromIterable(Collections.emptyList()));

        StepVerifier.create(accountService.withdrawalAccount(request))
                .expectNextMatches(res ->
                        res.getUserId().equals(userId) &&
                                res.getWithdrawalDetail().get(0).getStatus().equals(Constants.STATUS.SUCCESS)
                                && res.getWithdrawalDetail().get(0).getMessage().equals(Constants.STATUS_MESSAGE.NOT_FOUND)
                )
                .verifyComplete();
    }

}
