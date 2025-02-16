package com.assignment.test.controller;

import com.assignment.test.dto.request.DepositRequest;
import com.assignment.test.dto.request.WithdrawalRequest;
import com.assignment.test.dto.response.DepositResponse;
import com.assignment.test.dto.response.WithdrawalResponse;
import com.assignment.test.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/depositAcc")
    public Mono<DepositResponse> depositAcc(@RequestBody @Valid DepositRequest req) {
        return accountService.depositAccount(req);
    }

    @PostMapping("/withdrawalAcc")
    public Mono<WithdrawalResponse> withdrawalAcc(@RequestBody @Valid WithdrawalRequest req) {
        return accountService.withdrawalAccount(req);
    }
}
