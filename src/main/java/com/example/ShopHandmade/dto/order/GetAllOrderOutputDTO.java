package com.example.ShopHandmade.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.ShopHandmade.dto.orderItem.GetAllOrderItemOutputDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllOrderOutputDTO {
    private short id;
    private String status;
    private LocalDateTime orderDate;
    private String address;
    private List<GetAllOrderItemOutputDTO> listOrderItems;
    private Double totalAmount;
}
