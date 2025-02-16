package com.assignment.test.dto.request;

import com.assignment.test.dto.WithdrawalDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalRequest {

    @NotEmpty(message = "userId is required")
    private String userId;
    @NotEmpty(message = "accountId is required")
    public String accountId;
    @NotEmpty(message = "withdrawalDetail is required")
    @Valid
    public List<WithdrawalDTO> withdrawalDetail;
}
