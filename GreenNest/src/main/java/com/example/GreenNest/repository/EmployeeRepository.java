package com.example.GreenNest.repository;

import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Employee findByUserProfile(UserProfile userProfile);



}
