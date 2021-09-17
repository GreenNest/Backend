package com.example.GreenNest.repository;


import com.example.GreenNest.model.SupplierDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierDetails, Integer> {

    @Query("SELECT s.supplier_id FROM SupplierDetails s WHERE s.email = ?1")
    int getProfileId(String email);

    @Query("SELECT s.email FROM SupplierDetails s WHERE s.email = ?1")
    String getProfileEmail(String email);

}
