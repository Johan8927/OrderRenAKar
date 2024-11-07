// OrderServiceImpl.java
package fr.campus.rentakar.services;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.Vehicule;
import fr.campus.rentakar.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final int CAUTION_RATE = 10;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Value("${service.vehicles.url}")
    private String vehicleServiceUrl;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Vehicule getVehiculeDetails(Long vehiculeId) {

        String url = vehicleServiceUrl + vehiculeId;
        return restTemplate.getForObject(url, Vehicule.class);
    }

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
        boolean hasActiveOrder = orderRepository.existsByUserIdAndHasBeenPayedFalse(order.getUserId());
        if (hasActiveOrder) {
            throw new RuntimeException("Vous ne pouvez commander qu'un seul véhicule à la fois!");
        }

        Vehicule vehicule = getVehiculeDetails(order.getVehiculeId());
        if (!vehicule.isAvailable()) {
            throw new RuntimeException("Véhicule non disponible");
        }

        LocalDate today = LocalDate.now();
        if (order.getStartingOrderDate().isBefore(today) || order.getEndingOrderDate().isBefore(today)) {
            throw new RuntimeException("Les dates de prêt doivent être dans le futur.");
        }

        if (order.getStartingOrderDate().isAfter(order.getEndingOrderDate())) {
            throw new RuntimeException("La date de début doit être avant la date de fin.");
        }

        int caution = (int) (order.getEndingOrderDate().toEpochDay() - order.getStartingOrderDate().toEpochDay()) * CAUTION_RATE;
        order.setCaution(caution);
        vehicule.setIsAvailable(false);

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

        existingOrder.setUserId(updatedOrder.getUserId());
        existingOrder.setVehiculeId(updatedOrder.getVehiculeId());
        existingOrder.setStartingOrderDate(updatedOrder.getStartingOrderDate());
        existingOrder.setEndingOrderDate(updatedOrder.getEndingOrderDate());
        existingOrder.setHasBeenPayed(updatedOrder.isHasBeenPayed());
        existingOrder.setCaution(updatedOrder.getCaution());

        return orderRepository.save(existingOrder);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByVehicleId(Long vehicleId) {
        return List.of();
    }
}
