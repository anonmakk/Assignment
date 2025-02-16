package com.assignment.test.dto.request;

import com.assignment.test.dto.DepositDetailDTO;
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
public class DepositRequest {

    @NotEmpty(message = "userId is required")
    private String userId;
    @NotEmpty(message = "depositDetail is required")
    @Valid
    private List<DepositDetailDTO> depositDetail;
}
