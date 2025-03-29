package com.example.ShopHandmade.dto.orderItem;

import com.example.ShopHandmade.dto.product.GetProductInfoOutputDTO;
import lombok.*;

@Builder
@Data
public class GetDetailOrderItemOutputDTO {
    private short id;
    private GetProductInfoOutputDTO product;
    private int quantity;
    private Double totalPrice;
}
