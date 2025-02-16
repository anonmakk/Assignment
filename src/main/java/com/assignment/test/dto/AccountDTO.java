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
public class AccountDTO {
    @Column("account_id")
    private String accountId;
    @Column("user_id")
    private String userId;
    @Column("type")
    private String accountType;
    @Column("account_number")
    private String accountNumber;
    private String amount;
    @Column("flag_value")
    private String flagValue;
    @Column("is_main_account")
    private String isMain;
    private String color;
    private String progress;
}
