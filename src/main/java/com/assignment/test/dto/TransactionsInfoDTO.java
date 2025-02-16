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
public class TransactionsInfoDTO {

    @Column("name")
    private String tranName;
    @Column("image")
    private String tranImage;
}
