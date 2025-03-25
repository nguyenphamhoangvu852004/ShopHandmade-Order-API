package com.example.ShopHandmade.dto.order;

import lombok.*;

import java.time.LocalDateTime;

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
}
