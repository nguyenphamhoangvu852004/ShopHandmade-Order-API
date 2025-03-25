package com.example.ShopHandmade.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orderItems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "product_id", nullable = false)
    private short productId;

    @Column(name = "quantity", columnDefinition = "INT", nullable = false)
    private int quantity;
}
