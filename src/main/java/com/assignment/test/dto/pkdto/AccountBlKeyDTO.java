package com.assignment.test.dto.pkdto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBlKeyDTO {

    @Column("user_id")
    private String userId;
    @Column("account_id")
    private String accountId;
}
