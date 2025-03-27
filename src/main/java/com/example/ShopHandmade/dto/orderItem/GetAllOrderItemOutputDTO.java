package com.example.ShopHandmade.dto.orderItem;

import com.example.ShopHandmade.dto.product.GetProductInfoOutputDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllOrderItemOutputDTO {
    private short id;
    private GetProductInfoOutputDTO product;
    private int quantity;
}