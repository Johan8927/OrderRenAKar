package com.example.RentAKar.orderService;

import com.example.RentAKar.order.Order;
import com.example.RentAKar.orderRepository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

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

        if (order.getId() == null) {
            orderRepository.save(order);
            return order;
        } else {
            throw new RuntimeException("Order already exists with id: " + order.getId());
        }
    }

    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) orderRepository.deleteById(id);
        else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    public Order updateOrder(Long id, Order updatedOrder) {

        if (orderRepository.existsById(id)) {
            Order existingOrder = orderRepository.findById(id).get();
            existingOrder.setUserId(updatedOrder.getUserId());
            existingOrder.setVehiculeId(updatedOrder.getVehiculeId());
            existingOrder.setStartingOrderDate(updatedOrder.getStartingOrderDate());
            existingOrder.setHasBeenPayed(updatedOrder.isHasBeenPayed());
            existingOrder.setEndingOrderDate(updatedOrder.getEndingOrderDate());
            existingOrder.setCaution(updatedOrder.getCaution());
            return orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}
