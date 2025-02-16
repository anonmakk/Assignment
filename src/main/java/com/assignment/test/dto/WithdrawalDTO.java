package com.assignment.test.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalDTO {

    @NotEmpty(message = "accountNumber is required")
    public String accountNumber;
    @NotNull(message = "amount is required")
    @Min(value = 1,message ="amount must be greater than or equal to 1")
    public double amount;
}
