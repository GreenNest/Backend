package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Authority;
import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.EmployeeRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import com.example.GreenNest.response.DeliveryPersonResponse;
import com.example.GreenNest.response.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private PasswordEncoder bcryptEncoder;

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

    //add employee
    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean insertEmployee(@RequestBody Employee employee){
        if(employee == null){
            throw new ResourceNotFoundException("Missing Data Exception");
        }
        else{
            System.out.println(employee.getUserProfile().getEmail());

            List<String> employeeEmail = userProfileRepository.getProfile(employee.getUserProfile().getEmail());
            System.out.println(employeeEmail);

            if(employeeEmail.isEmpty()){
                employee.getUserProfile().setPassword(bcryptEncoder.encode(employee.getUserProfile().getPassword()));
                employeeRepository.save(employee);
                return true;
            }else{
                System.out.println("already have an account");
                return false;
            }
        }
    }

    //get employee by nic
    @GetMapping("/getEmployee/{nic}")
    public Optional<Employee> getEmployeeByNIC(@PathVariable String nic) {
        Optional<Employee> employee = employeeRepository.findById(nic);

        return employee;
    }

    //edit employee
    @PutMapping("/editEmployee/{nic}")
    public int editEmployee(@PathVariable String nic, @RequestBody EmployeeResponse employeeResponse) {
        String findNIC = employeeRepository.findIdByEmail(employeeResponse.getEmail());
        System.out.println(findNIC);
        System.out.println(nic);

        if(findNIC == null || findNIC == nic) {
            Employee employee = employeeRepository.findById(nic)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not exist"));

            employee.setFirst_name(employeeResponse.getFirst_name());
            employee.setLast_name(employeeResponse.getLast_name());
            employee.setAddress(employeeResponse.getAddress());
            employee.getUserProfile().setEmail(employeeResponse.getEmail());
            employee.setMobile(employeeResponse.getMobile());
            employeeRepository.save(employee);

            return 1;
        } else {
            return 0;
        }
    }

    //get delivery persons
    @GetMapping("/getDeliveryPersons")
    public List<DeliveryPersonResponse> getDeliveryPersons() {
        List<Employee> employees = employeeRepository.findAll();
        List<DeliveryPersonResponse> deliveryPersonResponses = new ArrayList<DeliveryPersonResponse>();

        for(int i=0; i<employees.size(); i++) {
            int status = employees.get(i).getAccount_status();
            if (status == 0) {
                List<Authority> roles = (List<Authority>) employees.get(i).getUserProfile().getAuthorities();
                for (int j = 0; j < roles.size(); j++) {
                    if (roles.get(j).getRoleCode().equals("delivery-person")) {
                        DeliveryPersonResponse deliveryPersonResponse = new DeliveryPersonResponse();

                        deliveryPersonResponse.setNic(employees.get(i).getNic());
                        deliveryPersonResponse.setFirst_name(employees.get(i).getFirst_name());
                        deliveryPersonResponse.setLast_name(employees.get(i).getLast_name());
                        deliveryPersonResponse.setAddress(employees.get(i).getAddress());
                        deliveryPersonResponse.setEmail(employees.get(i).getUserProfile().getEmail());
                        deliveryPersonResponse.setMobile(employees.get(i).getMobile());
                        deliveryPersonResponse.setAvailable(employees.get(i).getAvailable());

                        deliveryPersonResponses.add(deliveryPersonResponse);
                    }
                }
            }
        }
        return deliveryPersonResponses;
    }

}
