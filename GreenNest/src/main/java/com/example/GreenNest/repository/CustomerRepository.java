package com.example.GreenNest.repository;

import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Customer findByProfileEmail(String email);
    Customer findByProfile(UserProfile userProfile);

}
