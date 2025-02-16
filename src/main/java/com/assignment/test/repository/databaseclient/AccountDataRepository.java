package com.assignment.test.repository.databaseclient;

import com.assignment.test.dto.AccountBLDTO;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class AccountDataRepository {

    private DatabaseClient databaseClient;

    public AccountDataRepository(DatabaseClient databaseClient){
        this.databaseClient=databaseClient;
    }

    public Mono<Long> updateAccountBL(AccountBLDTO req){
       return databaseClient.sql("UPDATE account_balances SET amount= :amount Where user_id=:userId and account_id = :accountId ")
                .bind("amount",req.getAmount())
                .bind("userId",req.getUserId())
                .bind("accountId",req.getAccountId())
                .fetch()
                .rowsUpdated()
               .flatMap(r->r>0? Mono.just(r):Mono.error(new Exception("No data updated")));
    }
}
