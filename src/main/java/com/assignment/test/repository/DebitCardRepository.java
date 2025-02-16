package com.assignment.test.repository;

import com.assignment.test.dto.DebitCardInfoDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DebitCardRepository extends ReactiveCrudRepository<DebitCardInfoDTO,String> {

    @Query("select dc.card_id,dcd.number,\n" +
            "dcs.status,dcds.color ,dcds.border_color\n" +
            "from users u\n" +
            "left join debit_cards dc \n" +
            "on dc.user_id =u.user_id \n" +
            "-- and dc.card_id = ?\n" +
            "left join debit_card_status dcs \n" +
            "on dcs.user_id = dc.user_id \n" +
            "and dcs.card_id = dc.card_id \n" +
            "-- and dcs.status = Active/Inactive\n" +
            "left join debit_card_details dcd \n" +
            "on dcd.user_id = dc.user_id \n" +
            "and dcd.card_id = dc.card_id \n" +
            "-- and dcd.`number` = ?\n" +
            "left join debit_card_design dcds\n" +
            "on dcds.user_id = dc.user_id \n" +
            "and dcds.card_id = dc.card_id \n" +
            "where u.user_id = :userId ")
    Flux<DebitCardInfoDTO> findDebitCardInfo(String userId);

}
