package com.example.GreenNest.repository;

import com.example.GreenNest.model.Cart;
import com.example.GreenNest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomer(Customer customer);
}
