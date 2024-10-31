package com.example.RentAKar.orderRepository;

import com.example.RentAKar.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}

