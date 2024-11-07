package fr.campus.rentakar.services;

import fr.campus.rentakar.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order saveOrder(Order order);

    void deleteOrder(Long id);

    Order updateOrder(Long id, Order updatedOrder);

    List<Order> getOrdersByUserId(Long userId);

    List<Order> getOrdersByVehicleId(Long vehicleId);

}

