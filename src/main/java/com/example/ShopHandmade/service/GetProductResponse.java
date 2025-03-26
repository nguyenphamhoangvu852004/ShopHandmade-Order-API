package com.example.ShopHandmade.service;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Inventory inventory;
    private List<ImageDTO> images;


    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageDTO{
         private Long id;
        private String src; // Đúng với API
        private String alt;
        private int position;
    }
    // Getters và Setters
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Inventory {
    private Long id;
    private int stock;
    private String status;
    private Long productId;
    private String updatedAt;

    // Getters và Setters
}
