package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.*;
import com.assignment.test.dto.request.DepositRequest;
import com.assignment.test.dto.request.WithdrawalRequest;
import com.assignment.test.dto.response.DepositResponse;
import com.assignment.test.dto.response.ResponseTemplateDTO;
import com.assignment.test.dto.response.WithdrawalResponse;
import com.assignment.test.repository.AccountRepository;
import com.assignment.test.repository.databaseclient.AccountDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.Collections;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDataRepository accountDataRepository;

    public Mono<StatusInfoDTO<AccountInfoDTO>> getAccountInfo(String userId) {
        return accountRepository.findAccountInfo(userId)
                .map(r -> {
                    return AccountInfoDTO.builder()
                            .accountId(r.getAccountId())
                            .accountType(r.getAccountType())
                            .accountNumber(r.getAccountNumber())
                            .amount(r.getAmount())
                            .flagValue(r.getFlagValue())
                            .isMain(r.getIsMain().equals("1") ? true : false)
                            .color(r.getColor())
                            .progress(r.getProgress())
                            .build();
                })
                .collectList()
                .flatMap(f -> Mono.just(f.isEmpty()
                                ? new StatusInfoDTO<AccountInfoDTO>(Constants.STATUS.SUCCESS, Constants.STATUS_MESSAGE.NOT_FOUND, Collections.emptyList())
                                : new StatusInfoDTO<AccountInfoDTO>(Constants.STATUS.SUCCESS, "", f)
                        )
                )
                .onErrorResume(e -> Mono.just(new StatusInfoDTO<AccountInfoDTO>(Constants.STATUS.ERROR, e.getMessage(), Collections.emptyList())));
    }

    public Mono<DepositResponse> depositAccount(DepositRequest req) {
        return Flux.fromIterable(req.getDepositDetail())
                .flatMap(f -> {
                    return accountRepository.findAccountBLInfo(req.getUserId(), f.getAccountId())
                            .flatMap(a -> {
                                Double totalAmount = f.getAmount() + a.getAmount();
                                AccountBLDTO accountBLDTO = AccountBLDTO.builder()
                                        .accountId(f.getAccountId())
                                        .userId(req.getUserId())
                                        .amount(Double.valueOf(new DecimalFormat(".##").format(totalAmount)))
                                        .accountNumber(f.getAccountNumber())
                                        .build();
                                return accountDataRepository.updateAccountBL(accountBLDTO)
                                        .map(ff ->
                                                new ResponseTemplateDTO<DepositDetailDTO>(Constants.STATUS.SUCCESS, ""
                                                        , DepositDetailDTO.builder()
                                                        .accountId(f.getAccountId())
                                                        .accountNumber(f.getAccountNumber())
                                                        .amount(f.getAmount())
                                                        .build())
                                        );
                            }).switchIfEmpty(Mono.just(
                                            new ResponseTemplateDTO<DepositDetailDTO>(Constants.STATUS.SUCCESS, Constants.STATUS_MESSAGE.NOT_FOUND
                                                    , DepositDetailDTO.builder()
                                                    .accountId(f.getAccountId())
                                                    .accountNumber(f.getAccountNumber())
                                                    .amount(f.getAmount())
                                                    .build()
                                            )
                                    )
                            ).onErrorResume(e -> Mono.just(
                                    new ResponseTemplateDTO<DepositDetailDTO>(Constants.STATUS.ERROR, e.getMessage()
                                            , DepositDetailDTO.builder()
                                            .accountId(f.getAccountId())
                                            .accountNumber(f.getAccountNumber())
                                            .amount(f.getAmount())
                                            .build()
                                    )
                            ));
                }).collectList()
                .map(m -> new DepositResponse(req.getUserId(), m));
    }

    @Transactional
    public Mono<WithdrawalResponse> withdrawalAccount(WithdrawalRequest req) {
        return Flux.fromIterable(req.getWithdrawalDetail())
                .flatMap(f -> accountRepository.findAccountBLInfo(req.getUserId(), req.getAccountId())
                        .flatMap(a ->
                                accountRepository.findByAccountNumber(req.getUserId(), f.getAccountNumber())
                                        .flatMap(t -> {
                                            Double targetWd = t.getAmount() + f.getAmount();
                                            Double sourceWd = a.getAmount() - f.getAmount();
                                            AccountBLDTO targetAcc = AccountBLDTO.builder()
                                                    .accountId(t.getAccountId())
                                                    .accountNumber(t.getAccountNumber())
                                                    .userId(t.getUserId())
                                                    .amount(Double.valueOf(new DecimalFormat(".##").format(targetWd)))
                                                    .build();

                                            AccountBLDTO sourceAcc = AccountBLDTO.builder()
                                                    .accountId(a.getAccountId())
                                                    .accountNumber(a.getAccountNumber())
                                                    .userId(a.getUserId())
                                                    .amount(Double.valueOf(new DecimalFormat(".##").format(sourceWd)))
                                                    .build();
                                            return accountDataRepository.updateAccountBL(targetAcc)
                                                    .then(accountDataRepository.updateAccountBL(sourceAcc))
                                                    .thenReturn(new ResponseTemplateDTO<WithdrawalDTO>(
                                                            Constants.STATUS.SUCCESS, ""
                                                            , WithdrawalDTO.builder()
                                                            .accountNumber(targetAcc.getAccountNumber())
                                                            .amount(targetAcc.getAmount())
                                                            .build()
                                                    ));
                                        }).switchIfEmpty(Mono.just(
                                                        new ResponseTemplateDTO<WithdrawalDTO>(Constants.STATUS.SUCCESS, Constants.STATUS_MESSAGE.NOT_FOUND
                                                                , WithdrawalDTO.builder()
                                                                .accountNumber(f.getAccountNumber())
                                                                .amount(f.getAmount())
                                                                .build()
                                                        )
                                                )
                                        ).onErrorResume(e -> Mono.just(
                                                new ResponseTemplateDTO<WithdrawalDTO>(
                                                        Constants.STATUS.ERROR, e.getMessage()
                                                        , WithdrawalDTO.builder()
                                                        .accountNumber(f.getAccountNumber())
                                                        .amount(f.getAmount())
                                                        .build()
                                                ))
                                        )
                        )
                ).switchIfEmpty(Mono.just(
                                new ResponseTemplateDTO<WithdrawalDTO>(Constants.STATUS.SUCCESS, Constants.STATUS_MESSAGE.NOT_FOUND
                                        , null
                                )
                        )
                )
                .collectList()
                .map(m -> new WithdrawalResponse(req.getUserId(), req.getAccountId(), m));
    }
}
