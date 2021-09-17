package com.example.GreenNest.repository;

import com.example.GreenNest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("SELECT e.nic FROM Employee e WHERE e.userProfile.email = ?1")
    String findIdByEmail(String email);
}
