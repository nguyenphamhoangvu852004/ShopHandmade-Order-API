package com.example.ShopHandmade.service.order;

import com.example.ShopHandmade.dto.order.CreateOrderInputDTO;
import com.example.ShopHandmade.dto.order.DeleteOrderOutputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.dto.order.GetAllOrderOutputDTO;
import com.example.ShopHandmade.dto.order.GetDetailOrderOutputDTO;
import com.example.ShopHandmade.entity.OrderEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Page<GetAllOrderByAccountIdOutputDTO> getAllOrdersByAccountId(short accountId, Pageable pageable);

    Page<GetAllOrderOutputDTO> getAllOrders(Pageable pageable);

    OrderEntity getOrderById(short orderId);

    String createOrder(short accountId, CreateOrderInputDTO createOrderInputDTO);

    OrderEntity updateOrder(OrderEntity order);

    DeleteOrderOutputDTO deleteOrder(short orderId);

    boolean isOrderExist(short orderId);

    String updateStatusByOrderId(short orderId, String status);

    GetDetailOrderOutputDTO getDetailOrderByOrderId(short orderId);
}
