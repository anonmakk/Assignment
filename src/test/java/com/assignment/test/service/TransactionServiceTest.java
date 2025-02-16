package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.TransactionsInfoDTO;
import com.assignment.test.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    private final String userId = "test001";
    private final String tranName = "tn001";

    @BeforeEach
    public void setup() {
        TransactionsInfoDTO tranMock = new TransactionsInfoDTO(tranName, "Imtran01");
        Mockito.lenient().doReturn(Flux.fromIterable(List.of(tranMock)))
                .when(transactionRepository).findTransactionInfo(userId);
    }

    @Test
    public void getTransactionInfo_pass() {
        StepVerifier.create(transactionService.getTransactionInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.SUCCESS)
                                && !res.getInfo().isEmpty()
                                && res.getInfo().get(0).getTranName().equals(tranName)
                ).verifyComplete();
    }

    @Test
    public void getTransactionInfo_fail() {
        Mockito.doReturn(Flux.error(new RuntimeException("Error test01")))
                .when(transactionRepository).findTransactionInfo(userId);

        StepVerifier.create(transactionService.getTransactionInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.ERROR)
                                && res.getInfo().isEmpty()
                ).verifyComplete();
    }
}
