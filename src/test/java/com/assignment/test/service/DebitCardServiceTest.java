package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.DebitCardInfoDTO;
import com.assignment.test.repository.DebitCardRepository;
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
public class DebitCardServiceTest {

    @InjectMocks
    private DebitCardService debitCardService;

    @Mock
    private DebitCardRepository debitCardRepository;

    private final String userId = "Test01";
    private final String cardId = "cId001";
    private final String cardNo = "cNo001";

    @BeforeEach
    public void setup() {
        DebitCardInfoDTO debitMock = new DebitCardInfoDTO(cardId, cardNo, "Active", "c001", "B001");
        Mockito.lenient().doReturn(Flux.fromIterable(List.of(debitMock)))
                .when(debitCardRepository).findDebitCardInfo(userId);
    }

    @Test
    public void getDebitInfo_pass() {
        StepVerifier.create(debitCardService.getDebitInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.SUCCESS)
                                && !res.getInfo().isEmpty()
                                && res.getInfo().get(0).getCardNo().equals(cardNo)
                                && res.getInfo().get(0).getCardId().equals(cardId)
                ).verifyComplete();

    }

    @Test
    public void getDebitInfo_fail() {
        Mockito.doReturn(Flux.error(new RuntimeException("Database Error!")))
                .when(debitCardRepository).findDebitCardInfo(userId);

        StepVerifier.create(debitCardService.getDebitInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.ERROR)
                        && res.getMessage().equalsIgnoreCase("Database Error!")
                        && res.getInfo().isEmpty()
                ).verifyComplete();
    }
}
