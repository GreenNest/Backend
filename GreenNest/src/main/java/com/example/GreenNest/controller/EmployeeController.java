package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Authority;
import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.LeaveRequest;
import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.EmployeeRepository;
import com.example.GreenNest.repository.LeaveRequestRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.response.SalaryResponse;
import com.example.GreenNest.response.DeliveryPersonResponse;
import com.example.GreenNest.response.EmployeeResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

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

    //Get the employee salary
    @GetMapping(value = "/employee/salary/{type}")
    public ResponseEntity<Object> getEmployeeSalary(@PathVariable("type") int type) {
        List<Employee> employeeList = employeeRepository.findAll();
        List<Employee> employeeList1 = new ArrayList<Employee>();
        List<SalaryResponse> salaryResponseList = new ArrayList<SalaryResponse>();

        //get the today date
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate todayDate = LocalDate.now();
        String name = todayDate.toString().substring(0, 8);

        for (Employee e : employeeList) {
            int status = e.getAccount_status();
            if (status == 0) {
                List<String> roles = e.getUserProfile().getAuthorities().stream()
                        .map(item -> item.getAuthority()).collect(Collectors.toList());
                List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployee(e);
                List<LeaveRequest> leaveRequestList = new ArrayList<LeaveRequest>();
                for (LeaveRequest x : leaveRequests) {
                    String newDate1 = x.getFromDate();
                    String newDate2 = x.getToDate();
                    if (newDate1.contains(name) && newDate2.contains(name)) {
                        leaveRequestList.add(x);
                    }
                }
                long totalLeaves = 0;
                if (!leaveRequestList.isEmpty()) {
//                    System.out.println("leave list is not empty");
                    for (LeaveRequest l : leaveRequestList) {
                        final LocalDate leaveDate1 = LocalDate.parse(l.getFromDate(), formatter);
                        final LocalDate leaveDate2 = LocalDate.parse(l.getToDate(), formatter);
                        long count = ChronoUnit.DAYS.between(leaveDate1, leaveDate2);
                        totalLeaves = totalLeaves + count;
                    }
                }

//                System.out.println(totalLeaves);
                SalaryResponse salaryResponse = new SalaryResponse();
                salaryResponse.setName(e.getFirst_name());
                salaryResponse.setAddress(e.getAddress());
                salaryResponse.setEmail(e.getUserProfile().getEmail());
                salaryResponse.setMobile(e.getMobile());
                salaryResponse.setNic(e.getNic());
                if (type == 1) {
                    if (roles.contains("moderator")) {
//                        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployee(e);
//                        long totalLeaves = 0;
//                        for(LeaveRequest l : leaveRequests){
//                            final LocalDate leaveDate1 = LocalDate.parse(l.getFromDate() , formatter);
//                            final LocalDate leaveDate2 = LocalDate.parse(l.getToDate(), formatter);
//                            long count = ChronoUnit.DAYS.between(leaveDate1, leaveDate2);
//                            totalLeaves = totalLeaves+count;
//                        }
//                        System.out.println(totalLeaves);
                        long salary1 = 30000 - (500 * totalLeaves);
//                        SalaryResponse salaryResponse = new SalaryResponse();
                        salaryResponse.setSalary(salary1);
//                        salaryResponse.setName(e.getFirst_name());
//                        salaryResponse.setAddress(e.getAddress());
//                        salaryResponse.setEmail(e.getUserProfile().getEmail());
//                        salaryResponse.setMobile(e.getMobile());
//                        salaryResponse.setNic(e.getNic());

                        salaryResponseList.add(salaryResponse);
                    }
                } else if (type == 2) {
                    if (roles.contains("accountant")) {
                        long salary2 = 40000 - (500 * totalLeaves);
                        salaryResponse.setSalary(salary2);

                        salaryResponseList.add(salaryResponse);
                    }
                } else if (type == 3) {
                    if (roles.contains("delivery-person")) {
                        long salary2 = 35000 - (500 * totalLeaves);
                        salaryResponse.setSalary(salary2);

                        salaryResponseList.add(salaryResponse);
                    }
                } else if (type == 4) {
                    if (roles.contains("worker")) {
                        long salary2 = 35000 - (500 * totalLeaves);
                        salaryResponse.setSalary(salary2);

                        salaryResponseList.add(salaryResponse);
                    }
                }
            }
        }
        return ResponseHandle.response("employee salary", HttpStatus.OK, salaryResponseList);
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
