package com.example.GreenNest.repository;

import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findByCustomer(Customer customer);
}
