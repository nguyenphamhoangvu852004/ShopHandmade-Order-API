package com.example.ShopHandmade.service.order;


import com.example.ShopHandmade.dto.order.GetAllOrderByAccountIdOutputDTO;
import com.example.ShopHandmade.entity.OrderEntity;
import com.example.ShopHandmade.repository.order.IOrderRepository;
import com.example.ShopHandmade.repository.order.OrderRepositoryCus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements IOrderService{

    private IOrderRepository orderRepository;
    private OrderRepositoryCus orderRepositoryCus;

    public OrderServiceImp(IOrderRepository orderRepository, OrderRepositoryCus orderRepositoryCus) {
        this.orderRepository = orderRepository;
        this.orderRepositoryCus = orderRepositoryCus;
    }


    @Override
    public List<GetAllOrderByAccountIdOutputDTO> getAllOrdersByAccountId(short userId) {
        // gọi api bên account service để check xem có tồn tại account đó hay kooong ? neu khong thi return list rong

        // thực hiện gọi database
        try {
            List<OrderEntity> orders = this.orderRepositoryCus.getAllOrdersByAccountId(userId);
            List<GetAllOrderByAccountIdOutputDTO> listOrder = new ArrayList<>();
            for (OrderEntity order : orders) {
                GetAllOrderByAccountIdOutputDTO orderDTO = GetAllOrderByAccountIdOutputDTO.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .build();
                listOrder.add(orderDTO);
            }
            return listOrder;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        try {
            List<OrderEntity> orders = this.orderRepository.findAll();
            return orders;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public OrderEntity getOrderById(short orderId) {
        try {
            OrderEntity order = this.orderRepository.findById(orderId).get();
            return order;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public OrderEntity createOrder(OrderEntity order) {
        try {
            if (isOrderExist(order.getId())) throw new Exception("Order already exist");
            OrderEntity newOrder = this.orderRepository.save(order);
            return newOrder;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public OrderEntity updateOrder(OrderEntity order) {
        try {
            OrderEntity newOrder = this.orderRepository.save(order);
            return newOrder;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteOrder(short orderId) {
        try {
            this.orderRepository.deleteById(orderId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isOrderExist(short orderId) {
        return this.orderRepository.existsById(orderId);
    }


}
