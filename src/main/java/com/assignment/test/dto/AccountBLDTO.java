package com.assignment.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBLDTO {

    @Column("account_id")
    private String accountId;
    @Column("user_id")
    private String userId;
    @Column("amount")
    private Double amount;
    private String accountNumber;
}
