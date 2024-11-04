package com.example.RentAKar.services;

import com.example.RentAKar.model.Order;
import com.example.RentAKar.orderrepository.OrderRepository;
import com.example.RentAKar.orderrepository.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Méthode pour obtenir toutes les commandes
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Méthode pour obtenir une commande par ID
    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    // Méthode pour enregistrer une nouvelle commande
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Méthode pour supprimer une commande par ID
    @Override
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    // Méthode pour mettre à jour une commande existante
    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Optional<Order> existingOrderOpt = orderRepository.findById(id);
        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();
            existingOrder.setUserId(updatedOrder.getUserId());
            existingOrder.setVehiculeId(updatedOrder.getVehiculeId());
            existingOrder.setStartingOrderDate(updatedOrder.getStartingOrderDate());
            existingOrder.setEndingOrderDate(updatedOrder.getEndingOrderDate());
            existingOrder.setHasBeenPayed(updatedOrder.isHasBeenPayed());
            existingOrder.setCaution(updatedOrder.getCaution());
            return orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}
