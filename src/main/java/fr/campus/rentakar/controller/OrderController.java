package fr.campus.rentakar.controller;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.Vehicule;
import fr.campus.rentakar.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createOrder(@RequestBody Order order) {
        boolean saveOrder = orderService.saveOrder(order);
        // Logique de création d'une commande
        return new ResponseEntity<>(saveOrder ,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @PutMapping("/return/{id}")
    public boolean returnOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        order.setHasBeenPayed(true);
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // L'endpoint suivant est mis à jour pour être plus spécifique
    @GetMapping("/available/{startDate}/{endDate}")
    public List<Vehicule> getAvailableVehicules(@PathVariable String startDate, @PathVariable String endDate) {
        return orderService.getAvailableVehicules(startDate, endDate);  // Ajoutez la logique de service
    }

    // Récupérer tous les véhicules
    @GetMapping("/vehicle")
    public List<Vehicule> getAllVehicles() {
        return orderService.getAllVehicles();  // Ajoutez la logique de service
    }

    // Endpoint pour obtenir les détails d'un véhicule par son ID
    @GetMapping("/vehicle/{vehicleId}")
    public String getVehicleDetails(@PathVariable int vehicleId) {
        return orderService.getVehicleDetails(vehicleId);
    }
}
