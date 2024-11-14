package fr.campus.rentakar.services;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.User;
import fr.campus.rentakar.model.Vehicule;
import fr.campus.rentakar.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Value("${service.vehicles.url}")
    private String vehicleServiceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    // Modifié pour retourner un JSON sous forme de chaîne
    @Override
    public String getVehicleDetails(int vehicleId) {
        String url = vehicleServiceUrl + "/" + vehicleId;
        Vehicule vehicle = restTemplate.getForObject(url, Vehicule.class);
        if (vehicle == null) {
            throw new RuntimeException("Véhicule non trouvé avec l'ID: " + vehicleId);
        }
        // Retourne les détails du véhicule sous forme de chaîne JSON
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
    public List<Vehicule> getAvailableVehicules(String startDate, String endDate) {
        return List.of();
    }

    @Override
    public List<Vehicule> getAllVehicles() {
        return List.of();
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
        // Vérifier que l'ID de l'utilisateur est valide (exemple avec une validation simple)
        int userId = Math.toIntExact(order.getUserId());
        User user;

        // Vérifie que l'ID est un nombre et non null
        if (userId > 0) {  // Assurez-vous que l'ID est positif
            user = restTemplate.getForObject("http://localhost:8082/users/" + userId, User.class);
            if (user == null) {
                throw new RuntimeException("Utilisateur introuvable pour l'ID : " + userId);
            }
        } else {
            throw new IllegalArgumentException("ID d'utilisateur invalide : " + userId);
        }

        // Vérification du véhicule
        Vehicule vehicule = restTemplate.getForObject(vehicleServiceUrl + "/" + order.getVehiculeId(), Vehicule.class);
        if (vehicule == null) {
            throw new RuntimeException("Véhicule introuvable pour l'ID : " + order.getVehiculeId());
        }

        // Vérification de la disponibilité du véhicule
        if (!vehicule.isAvailable(order.getStartingOrderDate(), order.getEndingOrderDate())) {
            throw new RuntimeException("Véhicule non disponible pendant les dates demandées.");
        }

        // Vérification de l'âge et des conditions pour la réservation
        int age = getAge(user, LocalDate.now());
        if ((age < 21 && vehicule.getHorsePower() >= 8) || (age < 25 && vehicule.getHorsePower() >= 13)) {
            throw new RuntimeException("Le client ne peut pas réserver ce véhicule.");
        }

        try {
            // URL de l'API qui permet de créer une commande (sans ID dans l'URL)
            String url = "http://localhost:9090/orders"; // Le service REST qui accepte une commande POST

            // Envoi de la requête POST avec l'objet `Order` dans le corps de la requête
            restTemplate.postForObject(url, order, Order.class);

            return true;
        } catch (Exception e) {
            System.out.println("Error in saveOrder: " + e.getMessage());
            return false;
        }
    }


    private int getAge(User order, LocalDate today) {
        LocalDate endingOrderDate = order.getEndingOrderDate();
        if (endingOrderDate == null) {
            throw new IllegalArgumentException("La date de fin de commande est nulle !");
        }

        // Logique pour une date passée
        if (order.getStartingOrderDate().isBefore(Instant.from(today)) || order.getEndingOrderDate().isBefore(today)) {
            throw new RuntimeException("Les dates de prêt doivent être dans le futur.");
        }


        // Calcul de l'âge du client basé sur sa date de naissance

        LocalDate birthDate = LocalDate.parse(User.getDateOfBirth());
        int age = today.getYear() - birthDate.getYear();
        if (birthDate.plusYears(age).isAfter(today)) {
            age--;
        }

        if (age < 18) {
            throw new RuntimeException("Le client doit avoir au moins 18 ans et un permis de conduire pour réserver un véhicule.");
        }
        return age;
    }

    private float finalPrice(Order order) {
        LocalDate startingOrderDate = order.getStartingOrderDate();
        LocalDate endingOrderDate = order.getEndingOrderDate();
        int days = (int) ChronoUnit.DAYS.between(startingOrderDate, endingOrderDate);

        Vehicule vehicule = restTemplate.getForObject(vehicleServiceUrl + "/" + order.getVehiculeId(), Vehicule.class);
        int kilometerEstimate = order.getKilometerEstimate();

        assert vehicule != null;
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
        return List.of();  // Vous pouvez compléter cette méthode selon vos besoins
    }

    @Override
    public List<Order> getOrdersByVehicleId(Long vehicleId) {
        return List.of();  // Vous pouvez compléter cette méthode selon vos besoins
    }
}
