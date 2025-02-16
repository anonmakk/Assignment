package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.*;
import com.assignment.test.dto.request.InfoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class InfoServiceTest {

    @InjectMocks
    private InfoService infoService;

    @Mock
    private AccountService accountService;
    @Mock
    private DebitCardService debitCardService;
    @Mock
    private BannerService bannerService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private UserService userService;

    private final String userId = "test1";

    @Test
    void getInfo_Pass() {

        String status = Constants.STATUS.SUCCESS;
        String msg = "";

        Mockito.doReturn(
                        Mono.just(new StatusInfoDTO(status, msg,
                                List.of(new AccountInfoDTO("mock001", "mock_type", "mkNum", "1", "F1", true, "1", "1")))))
                .when(accountService).getAccountInfo(userId);

        Mockito.doReturn(Mono.just(new StatusInfoDTO(status, msg, List.of(new DebitCardInfoDTO("cardId001", "cardNo001", "Active", "c001", "bc001")))))
                .when(debitCardService).getDebitInfo(userId);

        Mockito.doReturn(Mono.just(new StatusInfoDTO(status, msg, List.of(new BannersInfoDTO("title001", "des001", "im01")))))
                .when(bannerService).getBannerInfo(userId);

        Mockito.doReturn(Mono.just(new StatusInfoDTO(status, msg, List.of("tran001", "tranIm001"))))
                .when(transactionService).getTransactionInfo(userId);

        Mockito.doReturn(Mono.just(new StatusInfoDTO(status, msg, List.of(new UsersInfoDTO("test1", "Hi test1")))))
                .when(userService).getUserInfo(userId);

        StepVerifier
                .create(infoService.getInfo(new InfoRequest(userId)))
                .expectNextMatches(res ->
                        (res.getAccountInfo().getStatus().equalsIgnoreCase(status) && !res.getAccountInfo().getInfo().isEmpty())
                                && (res.getDebitCardInfo().getStatus().equalsIgnoreCase(status) && !res.getDebitCardInfo().getInfo().isEmpty())
                                && (res.getBannersInfo().getStatus().equalsIgnoreCase(status) && !res.getBannersInfo().getInfo().isEmpty())
                                && (res.getTransactionsInfo().getStatus().equalsIgnoreCase(status) && !res.getTransactionsInfo().getInfo().isEmpty())
                                && (res.getUsersInfo().getStatus().equalsIgnoreCase(status) && !res.getUsersInfo().getInfo().isEmpty()))
                .verifyComplete();
    }

    @Test
    void getInfo_Fail() {

        String status_e = Constants.STATUS.ERROR;
        String status_s = Constants.STATUS.SUCCESS;
        String msg_s = Constants.STATUS_MESSAGE.NOT_FOUND;

        Mockito.doReturn(
                        Mono.just(new StatusInfoDTO(status_e, "Database connection failed!",
                                Collections.emptyList())))
                .when(accountService).getAccountInfo(userId);
        Mockito.doReturn(Mono.just(new StatusInfoDTO(status_s, msg_s, Collections.emptyList())))
                .when(debitCardService).getDebitInfo(userId);

        Mockito.doReturn(Mono.just(new StatusInfoDTO(status_e, "Something went wrong!", Collections.emptyList())))
                .when(bannerService).getBannerInfo(userId);

        Mockito.doReturn(Mono.just(new StatusInfoDTO(status_s, msg_s, Collections.emptyList())))
                .when(transactionService).getTransactionInfo(userId);

        Mockito.doReturn(Mono.just(new StatusInfoDTO(status_s, msg_s, Collections.emptyList())))
                .when(userService).getUserInfo(userId);

        StepVerifier
                .create(infoService.getInfo(new InfoRequest(userId)))
                .expectNextMatches(res ->
                        (res.getAccountInfo().getStatus().equalsIgnoreCase(status_e) && res.getAccountInfo().getInfo().isEmpty())
                                && (res.getDebitCardInfo().getStatus().equalsIgnoreCase(status_s) && res.getDebitCardInfo().getInfo().isEmpty())
                                && (res.getBannersInfo().getStatus().equalsIgnoreCase(status_e) && res.getBannersInfo().getInfo().isEmpty())
                                && (res.getTransactionsInfo().getStatus().equalsIgnoreCase(status_s) && res.getTransactionsInfo().getInfo().isEmpty())
                                && (res.getUsersInfo().getStatus().equalsIgnoreCase(status_s) && res.getUsersInfo().getInfo().isEmpty()))
                .verifyComplete();
    }
}
