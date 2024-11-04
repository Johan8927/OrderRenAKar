package com.example.RentAKar.ordercontroller;

import com.example.RentAKar.model.Order;
import com.example.RentAKar.orderrepository.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    // Constructor-based dependency injection
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

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        // Check if the order exists before updating
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder == null) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        order.setId(id); // Set the ID for the update
        return orderService.saveOrder(order);
    }

    @PutMapping("/return/{id}")
    public Order returnOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        order.setHasBeenPayed(true);
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderService.deleteOrder(id);
    }
}
