package com.assignment.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDTO {

    private String accountId;
    private String accountType;
    private String accountNumber;
    private String amount;
    private String flagValue;
    private boolean isMain;
    private String color;
    private String progress;

}
