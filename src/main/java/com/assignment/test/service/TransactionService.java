package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.BannersInfoDTO;
import com.assignment.test.dto.StatusInfoDTO;
import com.assignment.test.dto.TransactionsInfoDTO;
import com.assignment.test.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Mono<StatusInfoDTO<TransactionsInfoDTO>> getTransactionInfo(String userId) {
        return transactionRepository.findTransactionInfo(userId)
                .collectList()
                .flatMap(f -> Mono.just(f.isEmpty()
                                ? new StatusInfoDTO<TransactionsInfoDTO>(Constants.STATUS.SUCCESS, Constants.STATUS_MESSAGE.NOT_FOUND, Collections.emptyList())
                                : new StatusInfoDTO<TransactionsInfoDTO>(Constants.STATUS.SUCCESS, "", f)
                        )
                )
                .onErrorResume(e -> Mono.just(new StatusInfoDTO<TransactionsInfoDTO>(Constants.STATUS.ERROR, e.getMessage(), Collections.emptyList())));
    }
}
