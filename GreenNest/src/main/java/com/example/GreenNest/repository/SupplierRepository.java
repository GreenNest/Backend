package com.example.GreenNest.repository;


import com.example.GreenNest.model.SupplierDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierDetails, Integer> {

}
