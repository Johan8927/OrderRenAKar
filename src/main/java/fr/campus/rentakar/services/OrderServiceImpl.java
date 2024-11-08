package fr.campus.rentakar.services;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.User;
import fr.campus.rentakar.model.Vehicule;
import fr.campus.rentakar.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public Vehicule getVehiculeDetails(int vehiculeId) {
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
    public boolean saveOrder(Order order) {
        boolean hasActiveOrder = orderRepository.existsByUserIdAndHasBeenPayedFalse((long) order.getUserId());
        if (hasActiveOrder) {
            throw new RuntimeException("Vous ne pouvez commander qu'un seul véhicule à la fois!");
        }

        Vehicule vehicule = getVehiculeDetails(order.getVehiculeId());
        if (!vehicule.isAvailable(order.getStartingOrderDate(), order.getEndingOrderDate())) {
            throw new RuntimeException("Véhicule non disponible pendant les dates demandées.");
        }

        LocalDate today = LocalDate.now();
        if (order.getStartingOrderDate().isBefore(today) || order.getEndingOrderDate().isBefore(today)) {
            throw new RuntimeException("Les dates de prêt doivent être dans le futur.");
        }

        if (order.getStartingOrderDate().isAfter(order.getEndingOrderDate())) {
            throw new RuntimeException("La date de début doit être avant la date de fin.");
        }

        LocalDate birthDate = LocalDate.parse(User.getDateOfBirth());
        int age = today.getYear() - birthDate.getYear();
        if (birthDate.plusYears(age).isAfter(today)) {
            age--;
        }

        if (age < 18) {
            throw new RuntimeException("Le client doit avoir au moins 18 ans et un permis de conduire pour réserver un véhicule.");
        }

        int horsePowers = vehicule.getHorsePower();
        if ((age < 21 && horsePowers >= 8) || (age < 25 && horsePowers >= 13)) {
            throw new RuntimeException("Le client ne peut pas réserver un véhicule de cette puissance fiscale.");
        }

        orderRepository.save(order);
        return true;
    }

    private float finalPrice(Order order) {
        LocalDate startingOrderDate = order.getStartingOrderDate();
        LocalDate endingOrderDate = order.getEndingOrderDate();
        int days = (int) ChronoUnit.DAYS.between(startingOrderDate, endingOrderDate);

        Vehicule vehicule = getVehiculeDetails(order.getVehiculeId());
        int kilometerEstimate =  order.getKilometerEstimate();

        float price = getPrice(vehicule, kilometerEstimate);

        if (days > 1) {
            price += (days - 1) * 5;
        }
        return price;
    }

    private float getPrice(Vehicule vehicule, int kilometerEstimate) {
        float priceBase = 10.0f;
        float kilometerPrice = 5.0f;
        return switch (vehicule.getType()) {
            case "Voiture" -> priceBase + (kilometerPrice * kilometerEstimate);
            case "Deux Roues" -> priceBase + (vehicule.getHorsePower() * 0.001f * kilometerPrice * kilometerEstimate);
            case "Utilitaire" -> priceBase + (vehicule.getVolume() * 0.05f * kilometerPrice * kilometerEstimate);
            default -> throw new RuntimeException("Type de véhicule inconnu pour le calcul du prix.");
        };
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
        existingOrder.setKilometerEstimate(updatedOrder.getKilometerEstimate());

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
