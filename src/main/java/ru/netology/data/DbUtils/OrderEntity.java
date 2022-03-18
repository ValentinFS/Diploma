package ru.netology.data.DbUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEntity {
    private int id;
    private String created;
    private int creditId;
    private int paymentId;
}
