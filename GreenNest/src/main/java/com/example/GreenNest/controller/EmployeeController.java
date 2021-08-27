package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Authority;
import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.EmployeeRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //get employees by type
    @GetMapping("/viewEmployees/{userType}")
    public List<Employee> getEmployeesByType(@PathVariable int userType){
        List<Employee> empList = employeeRepository.findAll();
        List<Employee> filterList= new ArrayList<Employee>();

        for(int i=0; i<empList.size(); i++){
            int status = empList.get(i).getAccount_status();
            if(status == 0) {
                List<Authority> roles = (List<Authority>) empList.get(i).getUserProfile().getAuthorities();
                for (int j = 0; j < roles.size(); j++) {
                    if (userType == 1) {
                        if (roles.get(j).getRoleCode().equals("moderator")) {
                            filterList.add(empList.get(i));
                        }
                    } else if (userType == 2) {
                        if (roles.get(j).getRoleCode().equals("accountant")) {
                            filterList.add(empList.get(i));
                        }
                    } else if (userType == 3) {
                        if (roles.get(j).getRoleCode().equals("delivery-person")) {
                            filterList.add(empList.get(i));
                        }
                    } else if (userType == 4) {
                        if (roles.get(j).getRoleCode().equals("worker")) {
                            filterList.add(empList.get(i));
                        }
                    } else {
                        System.out.println("theja");
                    }
                }
            }
        }
        return filterList;
    }

    //delete employees
    @PutMapping("/deleteEmployee/{id}")
    public int deleteEmployee(@PathVariable String id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist"));

        employee.setAccount_status(1);

        Employee deleteEmployee = employeeRepository.save(employee);

        if(deleteEmployee.getAccount_status() == 1){
            return 1;
        }
        return 0;
    }

}
