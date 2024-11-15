package fr.campus.rentakar.services;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.Vehicle;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    /**
     * Récupère toutes les commandes.
     *
     * @return Liste de toutes les commandes.
     */
    List<Order> getAllOrders();

    /**
     * Récupère une commande par son ID.
     *
     * @param id Identifiant de la commande.
     * @return Commande correspondante ou null si non trouvée.
     */
    Order getOrderById(Long id);

    /**
     * Enregistre ou met à jour une commande.
     *
     * @param order Commande à enregistrer.
     * @return true si l'opération est réussie, sinon false.
     */
    boolean saveOrder(Order order);

    /**
     * Supprime une commande par son ID.
     *
     * @param id Identifiant de la commande à supprimer.
     */
    void deleteOrder(Long id);

    /**
     * Met à jour une commande existante.
     *
     * @param id            Identifiant de la commande à mettre à jour.
     * @param updatedOrder  Commande mise à jour.
     * @return Commande mise à jour.
     */
    Order updateOrder(Long id, Order updatedOrder);

    /**
     * Récupère les détails d'un véhicule par son ID.
     *
     * @param vehicleId Identifiant du véhicule.
     * @return Détails du véhicule sous forme de chaîne.
     */
    String getVehicleDetails(Long vehicleId);

    /**
     * Récupère les véhicules disponibles entre deux dates.
     *
     * @param startDate Date de début de disponibilité.
     * @param endDate   Date de fin de disponibilité.
     * @return Liste des véhicules disponibles.
     */
    List<Vehicle> getAvailableVehicules(LocalDate startDate, LocalDate endDate);

    String getVehicleDetails(int vehicleId);

    List<Vehicle> getAvailableVehicules(String startDate, String endDate);

    /**
     * Récupère tous les véhicules.
     *
     * @return Liste de tous les véhicules.
     */
    List<Vehicle> getAllVehicles();

    /**
     * Récupère les commandes associées à un utilisateur spécifique.
     *
     * @param userId Identifiant de l'utilisateur.
     * @return Liste des commandes de l'utilisateur.
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * Récupère les commandes associées à un véhicule spécifique.
     *
     * @param vehicleId Identifiant du véhicule.
     * @return Liste des commandes liées au véhicule.
     */
    List<Order> getOrdersByVehicleId(Long vehicleId);
}
