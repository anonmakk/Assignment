package com.assignment.test.service;

import com.assignment.test.dto.request.InfoRequest;
import com.assignment.test.dto.response.InfoResponse;
import com.assignment.test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InfoService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private DebitCardService debitCardService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    public Mono<InfoResponse> getInfo(InfoRequest req) {
        return Mono.zip(
                accountService.getAccountInfo(req.getUserId()),
                debitCardService.getDebitInfo(req.getUserId()),
                bannerService.getBannerInfo(req.getUserId()),
                transactionService.getTransactionInfo(req.getUserId()),
                userService.getUserInfo(req.getUserId())
                )
                .map(tuple ->
                        new InfoResponse(tuple.getT1(),
                                tuple.getT2(),
                                tuple.getT3(),
                                tuple.getT4(),
                                tuple.getT5()));

    }
}
