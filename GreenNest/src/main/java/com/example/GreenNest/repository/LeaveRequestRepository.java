package com.example.GreenNest.repository;

import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

//    @Query("SELECT * FROM LeaveRequest  l WHERE l.fromDate LIKE ")
//    List<Employee> getLaves(String nic, char s);

    List<LeaveRequest> findByFromDateContaining(String x);

    List<LeaveRequest> findByEmployee(Employee e);


}
