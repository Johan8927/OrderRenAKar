package com.example.RentAKar.services;

import com.example.RentAKar.model.Order;
import com.example.RentAKar.orderrepository.OrderRepository;
import com.example.RentAKar.orderrepository.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Mise à jour des champs de l'ordre existant
        existingOrder.setUserId(updatedOrder.getUserId());
        existingOrder.setVehiculeId(updatedOrder.getVehiculeId());
        existingOrder.setStartingOrderDate(updatedOrder.getStartingOrderDate());
        existingOrder.setEndingOrderDate(updatedOrder.getEndingOrderDate());
        existingOrder.setHasBeenPayed(updatedOrder.isHasBeenPayed());
        existingOrder.setCaution(updatedOrder.getCaution());

        return orderRepository.save(existingOrder);
    }

}
