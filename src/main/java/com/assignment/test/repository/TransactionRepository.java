package com.assignment.test.repository;

import com.assignment.test.dto.TransactionsInfoDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<TransactionsInfoDTO,String> {

    @Query("select t.name,t.image from transactions t \n" +
            "where t.user_id = :userId ")
    Flux<TransactionsInfoDTO> findTransactionInfo(String userId);
}
