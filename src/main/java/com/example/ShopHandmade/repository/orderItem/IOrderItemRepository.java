package com.example.ShopHandmade.repository.orderItem;

import com.example.ShopHandmade.entity.OrderItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IOrderItemRepository extends JpaRepository<OrderItemEntity, Short> {
}
