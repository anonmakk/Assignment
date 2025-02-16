package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.BannersInfoDTO;
import com.assignment.test.dto.DebitCardInfoDTO;
import com.assignment.test.dto.StatusInfoDTO;
import com.assignment.test.repository.BannersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class BannerService {

    @Autowired
    private BannersRepository bannersRepository;

    public Mono<StatusInfoDTO<BannersInfoDTO>> getBannerInfo(String userId) {
        return bannersRepository.findBannerInfo(userId)
                .collectList()
                .flatMap(f -> Mono.just(f.isEmpty()
                                ? new StatusInfoDTO<BannersInfoDTO>(Constants.STATUS.SUCCESS,Constants.STATUS_MESSAGE.NOT_FOUND , Collections.emptyList())
                                : new StatusInfoDTO<BannersInfoDTO>(Constants.STATUS.SUCCESS, "", f)
                        )
                )
                .onErrorResume(e -> Mono.just(new StatusInfoDTO<BannersInfoDTO>(Constants.STATUS.ERROR, e.getMessage(), Collections.emptyList())));
    }
}
