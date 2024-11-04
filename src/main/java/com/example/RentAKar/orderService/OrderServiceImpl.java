package com.example.RentAKar.orderService;

import com.example.RentAKar.order.Order;
import com.example.RentAKar.orderRepository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    public void OrderService(OrderRepository orderRepository) {
        List<Order> orders = orderRepository.findAll();

    }


    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        if (orderRepository.existsById(id)) {
            return orderRepository.findById(id).get();
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    public Order saveOrder(Order order) {
        if (order.getId()!= null) {
            throw new RuntimeException("Order already has an id: " + order.getId());
        }
        if (order.getOrderDate() == null) {
            throw new RuntimeException("Order must have an order date");
        }
        if (order.getTotalAmount() == null) {
            throw new RuntimeException("Order must have a total amount");
        }
        if (order.getStatus() == null) {
            throw new RuntimeException("Order must have a status");
        }
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id))
        orderRepository.deleteById(id);
        else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            existingOrder.setStatus(updatedOrder.getStatus());
            return orderRepository.save(existingOrder);
        }
        return null;
    }


}
