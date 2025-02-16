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
public class DebitCardInfoDTO {

    @Column("card_id")
    private String cardId;
    @Column("number")
    private String cardNo;
    @Column("status")
    private String cardStatus;
    private String color;
    @Column("border_color")
    private String borderColor;
}
