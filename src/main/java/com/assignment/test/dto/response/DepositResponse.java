package com.assignment.test.dto.response;

import com.assignment.test.dto.DepositDetailDTO;
import com.assignment.test.dto.StatusInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {

    private String userId;
    private List<ResponseTemplateDTO<DepositDetailDTO>> depositDetail;

}
