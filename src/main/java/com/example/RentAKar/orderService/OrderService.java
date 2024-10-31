package com.example.RentAKar.orderService;

import com.example.RentAKar.order.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    void deleteOrder(Long id);
    Order updateOrder(Long id, Order updatedOrder);

    // Add any other necessary methods here

}
