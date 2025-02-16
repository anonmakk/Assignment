package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.StatusInfoDTO;
import com.assignment.test.dto.TransactionsInfoDTO;
import com.assignment.test.dto.UsersInfoDTO;
import com.assignment.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<StatusInfoDTO<UsersInfoDTO>> getUserInfo(String userId) {
        return userRepository.findUserInfo(userId)
                .collectList()
                .flatMap(f -> Mono.just(f.isEmpty()
                                ? new StatusInfoDTO<UsersInfoDTO>(Constants.STATUS.SUCCESS, Constants.STATUS_MESSAGE.NOT_FOUND, Collections.emptyList())
                                : new StatusInfoDTO<UsersInfoDTO>(Constants.STATUS.SUCCESS, "", f)
                        )
                )
                .onErrorResume(e -> Mono.just(new StatusInfoDTO<UsersInfoDTO>(Constants.STATUS.ERROR, e.getMessage(), Collections.emptyList())));
    }
}
