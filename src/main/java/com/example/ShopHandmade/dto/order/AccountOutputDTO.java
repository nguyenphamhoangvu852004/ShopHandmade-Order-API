package com.example.ShopHandmade.dto.order;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountOutputDTO {
    private int id;
    private String email;
    private String phoneNumber;
    private String role;
    
}
