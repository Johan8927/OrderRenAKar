package com.example.RentAKar.orderrepository;

import com.example.RentAKar.model.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order saveOrder(Order order);
    void deleteOrder(Long id);
    Order updateOrder(Long id, Order updatedOrder);
}
