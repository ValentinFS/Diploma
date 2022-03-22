package ru.netology.data.DbUtils;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentEntity {

    private String id;
    private String amount;
    private String created;
    private String status;
    private String transactionsId;

    public String getStatus() {
        return status;
    }
}
