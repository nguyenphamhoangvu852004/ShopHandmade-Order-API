package com.example.ShopHandmade.service.order;

import com.example.ShopHandmade.dto.order.CreateOrderInputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.entity.OrderEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Page<GetAllOrderByAccountIdOutputDTO> getAllOrdersByAccountId(short accountId, Pageable pageable);

    List<OrderEntity> getAllOrders();

    OrderEntity getOrderById(short orderId);

    String createOrder(short accountId, CreateOrderInputDTO createOrderInputDTO);

    OrderEntity updateOrder(OrderEntity order);

    void deleteOrder(short orderId);

    boolean isOrderExist(short orderId);
}
