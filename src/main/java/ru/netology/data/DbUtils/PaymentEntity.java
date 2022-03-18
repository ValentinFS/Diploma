package ru.netology.data.DbUtils;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentEntity {

    private int id;
    private String amount;
    private String created;
    private String status;
    private int transactionsId;

    public String getStatus() {
        return status;
    }
}
