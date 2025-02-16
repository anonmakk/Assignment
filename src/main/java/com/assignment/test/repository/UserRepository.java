package com.assignment.test.repository;

import com.assignment.test.dto.UsersInfoDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UsersInfoDTO,String> {

    @Query("select u.name ,ug.greeting from users u \n" +
            "inner join user_greetings ug \n" +
            "on ug.user_id =u.user_id \n" +
            "where u.user_id = :userInfo ")
    Flux<UsersInfoDTO> findUserInfo(String userId);
}
