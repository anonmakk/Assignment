package com.assignment.test.service;

import com.assignment.test.constants.Constants;
import com.assignment.test.dto.UsersInfoDTO;
import com.assignment.test.repository.UserRepository;
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
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final String userId = "user001";
    private final String userGreet = "Hi_user001";

    @BeforeEach
    public void setup(){
        UsersInfoDTO userMock = new UsersInfoDTO(userId,userGreet);
        Mockito.lenient().doReturn(Flux.fromIterable(List.of(userMock)))
                .when(userRepository).findUserInfo(userId);
    }

    @Test
    public void getUserInfo_pass(){
        StepVerifier.create(userService.getUserInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.SUCCESS)
                        && !res.getInfo().isEmpty()
                        && res.getInfo().get(0).getUserName().equals(userId)
                        && res.getInfo().get(0).getUserGreeting().equals(userGreet)
                        ).verifyComplete();
    }

    @Test
    public void getUserInfo_fail(){
        Mockito.doReturn(Flux.error(new RuntimeException("Test Error001")))
                .when(userRepository).findUserInfo(userId);

        StepVerifier.create(userService.getUserInfo(userId))
                .expectNextMatches(res ->
                        res.getStatus().equals(Constants.STATUS.ERROR)
                        && res.getMessage().equals("Test Error001")
                        && res.getInfo().isEmpty()
                        ).verifyComplete();
    }
}
