package com.example.RentAKar.ordercontroller;

import com.example.RentAKar.model.Order;
import com.example.RentAKar.model.Vehicule;
import com.example.RentAKar.orderrepository.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
//Le « Cross-origin resource sharing » (CORS) ou « partage des ressources entre multiples origines » (en français, moins usité) est un mécanisme qui consiste à ajouter des en-têtes HTTP afin de permettre à un agent utilisateur d'accéder à des ressources d'un serveur situé sur une autre origine que le site courant.
@RestController
//Un RestController est une annotation spécialisée dans Spring Framework utilisée principalement pour développer des services Web RESTful
@RequestMapping("/order") // mapper les requêtes aux méthodes du contrôleur
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Read
    @GetMapping // mapp toutes les commandes List<Order>
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}") // mapp tout les id de order
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("http://192.168.1.239:9091/vehicules")
    public List<Vehicule> getAllVehicules() {

        return List.of();
    }

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order) {

        return orderService.saveOrder(order);
    }

    // Update
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    // return une commande
    @PutMapping("/return/{id}")
    public Order returnOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id); // prend l'id
        order.setHasBeenPayed(true); // vérifie si la commande est payé avec un boolean
        return orderService.saveOrder(order);
    }

    // Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // supprime si pas de contenu
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // get les vehicles disponibles
    @GetMapping("/available/{startDate}/{endDate}")
    public List<Vehicule> getAvailableVehicules(@PathVariable String startDate, @PathVariable String endDate) {
        return List.of();
    }

    // get tous les vehicle
    @GetMapping("/véhicule")
    public List<Vehicule> getAllVehicles() {
        return List.of();
    }

}
