package com.assignment.test.validation;

import com.assignment.test.constants.ValidationConstant;
import com.assignment.test.dto.request.DepositRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ValidateAccount {

    public void validateFiled(String name){
        if(name.equalsIgnoreCase(ValidationConstant.ACCOUNT.VALIDATE_NAME.DEPOSIT)){

        }
    }

    public void validateDeposit(DepositRequest req){
        if(req.getDepositDetail().isEmpty()){

        }else if(!StringUtils.hasText(req.getUserId())){

        }
    }
}
