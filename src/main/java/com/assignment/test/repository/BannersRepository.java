package com.assignment.test.repository;

import com.assignment.test.dto.BannersInfoDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BannersRepository extends ReactiveCrudRepository<BannersInfoDTO,String> {

    @Query("select b.title,b.description,b.image from banners b \n" +
            "where b.user_id = :userId ")
    Flux<BannersInfoDTO> findBannerInfo(String userId);
}
