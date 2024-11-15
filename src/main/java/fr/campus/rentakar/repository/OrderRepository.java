package fr.campus.rentakar.repository;

import fr.campus.rentakar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Récupère les commandes d'un utilisateur pour une plage de dates, où la commande n'a pas été payée.
     *
     * @param startDate Date de début de la période.
     * @param endDate   Date de fin de la période.
     * @param userId    Identifiant de l'utilisateur.
     * @return Liste des commandes disponibles.
     */
    @Query("SELECT o FROM Order o WHERE o.startingOrderDate <= :startDate AND o.endingOrderDate >= :endDate AND o.userId = :userId AND o.hasBeenPayed = false")
    List<Order> findAvailableVehiculesByDatesAndUserId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") Long userId);

    /**
     * Récupère les commandes d'un utilisateur qui n'ont pas été payées.
     *
     * @param userId Identifiant de l'utilisateur.
     * @return Liste des commandes non payées de l'utilisateur.
     */
    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.hasBeenPayed = false")
    List<Order> findOrdersByUserIdAndNotPayed(@Param("userId") Long userId);

    /**
     * Trouve une commande par son ID.
     *
     * @param orderId Identifiant de la commande.
     * @return La commande correspondante.
     */

    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional<Order> findById(@Param("orderId") Long orderId);

    /**
     * Met à jour l'état d'une commande pour la marquer comme payée.
     *
     * @param orderId Identifiant de la commande à mettre à jour.
     * @return Le nombre de lignes affectées.
     */
    @Modifying
    @Query("UPDATE Order o SET o.hasBeenPayed = true WHERE o.id = :orderId")
    int updateOrderAsPayed(@Param("orderId") Long orderId);

    /**
     * Vérifie si un utilisateur a des commandes non payées.
     *
     * @param userId Identifiant de l'utilisateur.
     * @return true si l'utilisateur a des commandes non payées, sinon false.
     */
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Order o WHERE o.userId = :userId AND o.hasBeenPayed = false")
    boolean existsByUserIdAndHasBeenPayedFalse(@Param("userId") Long userId);

    /**
     * Trouve une commande par son ID.
     *
     * @param id Identifiant de la commande.
     * @return La commande correspondante.
     */
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Order findOrderById(@Param("id") Long id);
}
