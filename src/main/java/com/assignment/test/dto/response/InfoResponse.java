package com.assignment.test.dto.response;

import com.assignment.test.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse {

    private StatusInfoDTO<AccountInfoDTO> accountInfo;
    private StatusInfoDTO<DebitCardInfoDTO> debitCardInfo;
    private StatusInfoDTO<BannersInfoDTO> bannersInfo;
    private StatusInfoDTO<TransactionsInfoDTO> transactionsInfo;
    private StatusInfoDTO<UsersInfoDTO> usersInfo;
}
