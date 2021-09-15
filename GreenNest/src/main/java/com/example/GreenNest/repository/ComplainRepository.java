package com.example.GreenNest.repository;

import com.example.GreenNest.model.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {
}
