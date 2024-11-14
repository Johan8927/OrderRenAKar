package fr.campus.rentakar.repository;

import fr.campus.rentakar.model.Order;
import fr.campus.rentakar.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.startingOrderDate <= :startDate AND o.endingOrderDate >= :endDate AND o.userId = :userId AND o.hasBeenPayed = false")
    List<Order> findAvailableVehiculesByDatesAndUserId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") int userId);

    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.hasBeenPayed = false")
    List<Order> findOrdersByUserIdAndNotPayed(@Param("userId") int userId);

    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Order findById(@Param("orderId") int orderId);

    @Modifying
    @Query("UPDATE Order o SET o.hasBeenPayed = true WHERE o.id = :orderId")
    int updateOrderAsPayed(@Param("orderId") int orderId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Order o WHERE o.userId = :userId AND o.hasBeenPayed = false")
    boolean existsByUserIdAndHasBeenPayedFalse(@Param("userId") Long userId);

    // Requête pour trouver une commande par son ID
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Order findOrderById(@Param("id") Long id);


}
