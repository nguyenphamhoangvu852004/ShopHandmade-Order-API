package com.example.ShopHandmade.repository.order;

import com.example.ShopHandmade.entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IOrderRepository extends JpaRepository<OrderEntity, Short> {
}
