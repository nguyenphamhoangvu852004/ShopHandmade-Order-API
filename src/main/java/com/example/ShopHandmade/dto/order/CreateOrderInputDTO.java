package com.example.ShopHandmade.dto.order;

import java.util.List;

import com.example.ShopHandmade.dto.orderItem.CreateOrderItemInputDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderInputDTO {
    private String address;
    private List<CreateOrderItemInputDTO> listOrderItem;
}
