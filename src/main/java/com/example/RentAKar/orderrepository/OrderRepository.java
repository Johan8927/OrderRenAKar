package com.example.RentAKar.orderrepository;

import com.example.RentAKar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

///////////////////////////////////// nôtre bdd créer automatiquement avec JPA
public interface OrderRepository extends JpaRepository<Order, Long> {

}

