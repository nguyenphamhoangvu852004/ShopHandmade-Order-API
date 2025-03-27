package com.example.ShopHandmade.service.order;

import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.entity.OrderEntity;

import java.util.List;

public interface IOrderService {
    List<GetAllOrderByAccountIdOutputDTO> getAllOrdersByAccountId(short accountId);
    List<OrderEntity> getAllOrders();
    OrderEntity getOrderById(short orderId);
    OrderEntity createOrder(OrderEntity order);
    OrderEntity updateOrder(OrderEntity order);
    void deleteOrder(short orderId);

    boolean isOrderExist(short orderId);
}
