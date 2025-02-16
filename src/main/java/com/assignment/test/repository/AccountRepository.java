package com.assignment.test.repository;

import com.assignment.test.dto.AccountBLDTO;
import com.assignment.test.dto.AccountDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountDTO,String> {

    @Query("select a.account_id,a.type,a.account_number,\n" +
            "ab.amount, af.flag_value,\n" +
            "ad.is_main_account,ad.color ,ad.progress \n" +
            "from users u \n" +
            "left join accounts a \n" +
            "on a.user_id = u.user_id \n" +
            "left join account_balances ab \n" +
            "on ab.user_id =a.user_id \n" +
            "and ab.account_id =a.account_id \n" +
            "left join account_flags af \n" +
            "on af.user_id = a.user_id \n" +
            "and af.account_id =a.account_id \n" +
            "left join account_details ad \n" +
            "on ad.user_id = a.user_id \n" +
            "and ad.account_id =a.account_id \n" +
            "where u.user_id = :userId ")
    Flux<AccountDTO> findAccountInfo(String userId);

    @Query("select ab.amount,ab.user_id,ab.account_id " +
            "from account_balances ab where ab.user_id = :userId and account_id = :accountId")
    Flux<AccountBLDTO> findAccountBLInfo(String userId,String accountId);

    @Query("select a.account_id,a.user_id,ab.amount,a.account_number from accounts a \n" +
            "left join account_balances ab on ab.user_id =a.user_id \n" +
            "and ab.account_id =a.account_id \n" +
            "where a.user_id =:userId and a.account_number =:accountNumber ;")
    Flux<AccountBLDTO> findByAccountNumber(String userId,String accountNumber);
}
