package com.example.ShopHandmade.dto.orderItem;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderItemInputDTO {
    private short productId;
    private int quantity;      
}
