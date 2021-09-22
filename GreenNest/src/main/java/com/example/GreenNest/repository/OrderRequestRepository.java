package com.example.GreenNest.repository;

import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {

    List<OrderRequest> findByCustomer(Customer customer);
}
