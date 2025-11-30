package com.trackmyfix.trackmyfix.repository;

import com.trackmyfix.trackmyfix.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByNumber(String number);

    Optional<Order> findTopByOrderByIdOrderDesc();
}
