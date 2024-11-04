package com.example.RentAKar.orderrepository;

import com.example.RentAKar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}

