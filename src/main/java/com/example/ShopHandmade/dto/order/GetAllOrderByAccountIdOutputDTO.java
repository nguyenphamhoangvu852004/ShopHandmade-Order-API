package com.example.ShopHandmade.dto.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ShopHandmade.dto.orderItem.GetAllOrderItemOutputDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllOrderByAccountIdOutputDTO {
    private short id;
    private String status;
    private LocalDateTime orderDate;
    private List<GetAllOrderItemOutputDTO> listOrderItems;

}
