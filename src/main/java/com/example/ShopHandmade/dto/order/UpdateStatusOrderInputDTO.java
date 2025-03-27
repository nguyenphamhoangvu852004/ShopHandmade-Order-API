package com.example.ShopHandmade.dto.order;

import lombok.Data;

@Data
public class UpdateStatusOrderInputDTO {
    private short orderId;
    private String status;
}
