package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.BannersInfoDTO;
import com.assignment.test.repository.BannersRepository;
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
public class BannerServiceTest {

    @InjectMocks
    private BannerService bannerService;

    @Mock
    private BannersRepository bannersRepository;

    private final String userId = "user001";
    private final String title = "Hellow";

    @BeforeEach
    public void setup() {
        BannersInfoDTO bannMock = new BannersInfoDTO(title, "Hi hi hi", "im001");
        Mockito.lenient().doReturn(Flux.fromIterable(List.of(bannMock)))
                .when(bannersRepository).findBannerInfo(userId);
    }

    @Test
    public void getBannerInfo_pass() {
        StepVerifier.create(bannerService.getBannerInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.SUCCESS)
                                && !res.getInfo().isEmpty()
                                && res.getInfo().get(0).getTitle().equals(title)
                ).verifyComplete();
    }

    @Test
    public void getBannerInfo_fail() {
        Mockito.doReturn(Flux.error(new RuntimeException("Database Error!")))
                .when(bannersRepository).findBannerInfo(userId);

        StepVerifier.create(bannerService.getBannerInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.ERROR)
                                && res.getInfo().isEmpty()
                                && res.getMessage().equals("Database Error!")
                ).verifyComplete();
    }
}
