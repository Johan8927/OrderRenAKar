package com.example.RentAKar.services;

import com.example.RentAKar.model.Order;
import com.example.RentAKar.model.Vehicule;
import com.example.RentAKar.orderrepository.OrderRepository;
import com.example.RentAKar.orderrepository.OrderService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service  // Indique que c'est un service pour le partage des données
public class OrderServiceImpl implements OrderService {

    private static final int MIN_RENTAL_DAYS = 3;  // Constante pour la durée minimale de location
    private static final int CAUTION_RATE = 10;    // Constante pour le calcul de la caution
    private final RestTemplate restTemplate;

    public OrderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Vehicule getVehiculeDetails(Long vehiculeId) {
        String url = "http://localhost:9091/vehicules" + vehiculeId;
        return restTemplate.getForObject(url, Vehicule.class);
    }
    @Autowired // Injection automatique de dépendances
    private OrderRepository orderRepository;


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
    public Order saveOrder(@NotNull Order order) {
        // Vérifier si le client a déjà une commande active
        boolean hasActiveOrder = orderRepository.existsByUserIdAndHasBeenPayedFalse(order.getUserId());
        if (hasActiveOrder) {
            throw new RuntimeException("Vous ne pouvez commander qu'un seul véhicule à la fois!");
        }

        // Vérification de la disponibilité du véhicule
        Vehicule vehicule = (Vehicule) vehicule.getReferenceById(order.getVehiculeId());

        if (!vehicule.isAvailable()) {
            throw new RuntimeException("Véhicule non disponible");
        }

        // Vérification des dates de prêt
        LocalDate today = LocalDate.now();
        if (order.getStartingOrderDate().isBefore(today) || order.getEndingOrderDate().isBefore(today)) {
            throw new RuntimeException("Les dates de prêt doivent être dans le futur.");
        }

        // Vérification du temps de prêt
        if (order.getStartingOrderDate().isAfter(order.getEndingOrderDate())) {
            throw new RuntimeException("La date de début doit être avant la date de fin.");
        }

        // Calcul de la caution
        int caution = (int) (order.getEndingOrderDate().toEpochDay() - order.getStartingOrderDate().toEpochDay()) * CAUTION_RATE;
        order.setCaution(caution);  // Ajouter la caution à l'ordre

        // Marquer le véhicule comme non disponible
        vehicule.setIsAvailable(false);
        vehiculeRepository.save(vehicule);

        return orderRepository.save(order); // Enregistrer la commande dans la base de données
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id); // Suppression par Id
    }

    @Override
    public Order updateOrder(Long id, @NotNull Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Mise à jour des champs de l'ordre existant
        existingOrder.setUserId(updatedOrder.getUserId());
        existingOrder.setVehiculeId(updatedOrder.getVehiculeId());
        existingOrder.setStartingOrderDate(updatedOrder.getStartingOrderDate());
        existingOrder.setEndingOrderDate(updatedOrder.getEndingOrderDate());
        existingOrder.setHasBeenPayed(updatedOrder.isHasBeenPayed());
        existingOrder.setCaution(updatedOrder.getCaution());

        return orderRepository.save(existingOrder); // Sauvegarde dans la base de données
    }

}
