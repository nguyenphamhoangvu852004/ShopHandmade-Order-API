package com.example.ShopHandmade.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ShopHandmade.dto.orderItem.GetDetailOrderItemOutputDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDetailOrderOutputDTO {
    private short id;
    private String status;
    private LocalDateTime orderDate;
    private String address;
    List<GetDetailOrderItemOutputDTO> listDetailOrderItem;
    private Double totalAmount;
}
