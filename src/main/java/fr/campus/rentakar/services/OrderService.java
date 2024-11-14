package fr.campus.rentakar.services;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.Vehicule;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id);

    boolean saveOrder(Order order);

    void deleteOrder(Long id);

    Order updateOrder(Long id, Order updatedOrder);

    String getVehicleDetails(int vehicleId);

    List<Vehicule> getAvailableVehicules(String startDate, String endDate);

    List<Vehicule> getAllVehicles();
}

