package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.DebitCardInfoDTO;
import com.assignment.test.dto.StatusInfoDTO;
import com.assignment.test.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class DebitCardService {

    @Autowired
    private DebitCardRepository debitCardRepository;

    public Mono<StatusInfoDTO<DebitCardInfoDTO>> getDebitInfo(String userId) {
        return debitCardRepository.findDebitCardInfo(userId)
                .collectList()
                .flatMap(f -> Mono.just(f.isEmpty()
                                ? new StatusInfoDTO<DebitCardInfoDTO>(Constants.STATUS.SUCCESS, Constants.STATUS_MESSAGE.NOT_FOUND, Collections.emptyList())
                                : new StatusInfoDTO<DebitCardInfoDTO>(Constants.STATUS.SUCCESS, "", f)
                        )
                )
                .onErrorResume(e -> Mono.just(new StatusInfoDTO<DebitCardInfoDTO>(Constants.STATUS.ERROR, e.getMessage(), Collections.emptyList())));
    }
}
