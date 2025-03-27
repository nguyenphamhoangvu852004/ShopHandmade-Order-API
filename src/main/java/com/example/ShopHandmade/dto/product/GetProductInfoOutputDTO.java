package com.example.ShopHandmade.dto.product;

import java.util.List;

import lombok.Data;

@Data
public class GetProductInfoOutputDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private InventoryDTO inventory;
    private List<ImageDTO> images;

    @Data
    public static class InventoryDTO {
        private Long id;
        private Integer stock;
        private String status;
        private String updatedAt;
    }

    @Data
    public static class ImageDTO {
        private Long id;
        private String src;
        private String alt;
        private Integer position;
    }
}
