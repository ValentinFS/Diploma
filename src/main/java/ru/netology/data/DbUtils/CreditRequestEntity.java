package ru.netology.data.DbUtils;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreditRequestEntity {
    private int id;
    private int bankId;
    private String created;
    private String status;

}
