package fr.campus.rentakar.services;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.User;
import fr.campus.rentakar.model.Vehicle;
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

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Value("${service.vehicles.url}")
    private String vehicleServiceUrl;

    @Value("${service.users.url}")
    private String userServiceUrl;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getVehicleDetails(int vehicleId) {
        String url = vehicleServiceUrl + "/" + vehicleId;
        Vehicle vehicle = restTemplate.getForObject(url, Vehicle.class);
        if (vehicle == null) {
            throw new RuntimeException("Véhicule non trouvé avec l'ID: " + vehicleId);
        }
        return convertVehicleToJson(vehicle);
    }

    private String convertVehicleToJson(Vehicle vehicle) {
        return "{ \"id\": " + vehicle.getId() + ", " +
                "\"model\": \"" + vehicle.getModel() + "\", " +
                "\"horsePower\": " + vehicle.getHorsePower() + ", " +
                "\"type\": \"" + vehicle.getType() + "\", " +
                "\"available\": " + vehicle.isAvailable() + ", " +
                "\"displacement\": " + vehicle.getDisplacement() + ", " +
                "\"capacity\": " + vehicle.getCapacity() + ", " +
                "\"brand\": \"" + vehicle.getBrand() + "\", " +
                "\"color\": \"" + vehicle.getColor() + "\", " +
                "\"registration\": \"" + vehicle.getRegistration() + "\"}";
    }

    @Override
    public List<Vehicle> getAvailableVehicules(String startDate, String endDate) {
        // Vous pouvez compléter cette méthode pour interroger un autre service
        return List.of();
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        // Vous pouvez compléter cette méthode pour interroger un autre service
        return List.of();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + id));
    }

    @Override
    public boolean saveOrder(Order order) {
        User user = validateUser(order.getUserId());
        /*
        Vehicle vehicule = validateVehicule(order.getVehiculeId());

        validateVehiculeAvailability(vehicule, order);


        validateUserEligibility(user, vehicule);

         */
        orderRepository.save(order);
        return true;
    }

    private User validateUser(Long userId) {
        String url = userServiceUrl + "/" + userId;
        User user = restTemplate.getForObject(url, User.class);
        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable pour l'ID : " + userId);
        }
        return user;
    }

    private Vehicle validateVehicule(Long vehiculeId) {
        String url = vehicleServiceUrl + "/" + vehiculeId;
        Vehicle vehicule = restTemplate.getForObject(url, Vehicle.class);
        if (vehicule == null) {
            throw new RuntimeException("Véhicule introuvable pour l'ID : " + vehiculeId);
        }
        return vehicule;
    }

    private void validateVehiculeAvailability(Vehicle vehicule, Order order) {
        if (!vehicule.isAvailable(order.getStartingOrderDate(), order.getEndingOrderDate())) {
            throw new RuntimeException("Le véhicule n'est pas disponible pour les dates spécifiées.");
        }
    }

    private void validateUserEligibility(User user, Vehicle vehicule) {
        int age = calculateAge(User.getDateOfBirth(), LocalDate.now());
        if ((age < 21 && vehicule.getHorsePower() >= 8) || (age < 25 && vehicule.getHorsePower() >= 13)) {
            throw new RuntimeException("L'utilisateur ne remplit pas les conditions pour louer ce véhicule.");
        }
    }

    private int calculateAge(LocalDate birthDate, LocalDate today) {
        int age = today.getYear() - birthDate.getYear();
        if (birthDate.plusYears(age).isAfter(today)) {
            age--;
        }
        return age;
    }

    private float calculateFinalPrice(Order order, Vehicle vehicule) {
        long days = ChronoUnit.DAYS.between(order.getStartingOrderDate(), order.getEndingOrderDate());
        float basePrice = 10.0f;
        float pricePerKm = 5.0f;
        float totalPrice;

        switch (vehicule.getType()) {
            case "Voiture" -> totalPrice = basePrice + (pricePerKm * order.getKilometerEstimate());
            case "Deux Roues" ->
                    totalPrice = basePrice + (vehicule.getHorsePower() * 0.001f * pricePerKm * order.getKilometerEstimate());
            case "Utilitaire" ->
                    totalPrice = basePrice + (vehicule.getCapacity() * 0.05f * pricePerKm * order.getKilometerEstimate());
            default -> throw new RuntimeException("Type de véhicule inconnu pour le calcul du prix.");
        }

        if (days > 1) {
            totalPrice += (days - 1) * 5;
        }
        return totalPrice;
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Commande non trouvée avec l'ID : " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + id));

        existingOrder.setUserId(updatedOrder.getUserId());
        existingOrder.setVehiculeId(updatedOrder.getVehiculeId());
        existingOrder.setStartingOrderDate(updatedOrder.getStartingOrderDate());
        existingOrder.setEndingOrderDate(updatedOrder.getEndingOrderDate());
        existingOrder.setCaution(updatedOrder.getCaution());
        existingOrder.setKilometerEstimate(updatedOrder.getKilometerEstimate());

        return orderRepository.save(existingOrder);
    }

    @Override
    public String getVehicleDetails(Long vehicleId) {
        return "";
    }

    @Override
    public List<Vehicle> getAvailableVehicules(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        // Implémentez cette méthode selon vos besoins
        return List.of();
    }

    @Override
    public List<Order> getOrdersByVehicleId(Long vehicleId) {
        // Implémentez cette méthode selon vos besoins
        return List.of();
    }
}
