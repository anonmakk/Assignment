package com.assignment.test.dto.response;

import com.assignment.test.dto.WithdrawalDTO;
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
public class WithdrawalResponse {

    public String userId;
    public String accountNumber;
    public List<ResponseTemplateDTO<WithdrawalDTO>> withdrawalDetail;
}
