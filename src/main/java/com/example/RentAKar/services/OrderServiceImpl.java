package com.example.RentAKar.services;

import com.example.RentAKar.model.Order;
import com.example.RentAKar.orderrepository.OrderRepository;
import com.example.RentAKar.orderrepository.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service  // indique que c'est un service pour le partage des données
public class OrderServiceImpl implements OrderService {

    @Autowired // injection automatique de dépendances
    private OrderRepository orderRepository;

    // implémentation de l'interface OrderService
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll(); // findAll sur la bdd
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id) // findById sur la bdd
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order); // save sur la bdd
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) { // si pas de repos(commande) avec id
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id); // suppression par Id
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id) // regarde s'il y a un id de commande déjà présent dans la requête
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Mise à jour des champs de l'ordre existant
        existingOrder.setUserId(updatedOrder.getUserId());
        existingOrder.setVehiculeId(updatedOrder.getVehiculeId());
        existingOrder.setStartingOrderDate(updatedOrder.getStartingOrderDate());
        existingOrder.setEndingOrderDate(updatedOrder.getEndingOrderDate());
        existingOrder.setHasBeenPayed(updatedOrder.isHasBeenPayed());
        existingOrder.setCaution(updatedOrder.getCaution());

        return orderRepository.save(existingOrder); // enfin sauvegarde en bdd
    }

}
