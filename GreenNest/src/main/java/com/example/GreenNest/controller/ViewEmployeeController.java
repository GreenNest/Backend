package com.example.GreenNest.controller;

import com.example.GreenNest.model.Authority;
import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.EmployeeRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ViewEmployeeController {

    @Autowired
    private EmployeeRepository viewEmployee;

    @Autowired
    private UserProfileRepository userProfileRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return viewEmployee.findAll();
    }

    //get employees by type
    @GetMapping("/viewEmployees/{userType}")
    public   List<Employee> getEmployeesByType(@PathVariable int userType){
        List<Employee> empList = viewEmployee.findAll();
        List<Employee> filterList= new ArrayList<Employee>();

        for(int i=0; i<empList.size(); i++){
            List<Authority> roles = (List<Authority>) empList.get(i).getUserProfile().getAuthorities();
            for(int j=0; j<roles.size(); j++){
                if(userType == 1){
                    if(roles.get(j).getRoleCode().equals("moderator")){
                        filterList.add(empList.get(i));
                    }
                } else if(userType == 2){
                    if(roles.get(j).getRoleCode().equals("accountant")){
                        filterList.add(empList.get(i));
                    }
                } else if(userType == 3){
                    if(roles.get(j).getRoleCode().equals("delivery-person")){
                        filterList.add(empList.get(i));
                    }
                } else if(userType == 4){
                    if(roles.get(j).getRoleCode().equals("worker")){
                        filterList.add(empList.get(i));
                    }
                } else{
                    System.out.println("theja");
                }
            }
        }
        return filterList;
    }
}
