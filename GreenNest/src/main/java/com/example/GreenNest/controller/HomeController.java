package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.CustomerRepository;
import com.example.GreenNest.repository.EmployeeRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import com.example.GreenNest.request.AuthenticationRequest;
import com.example.GreenNest.request.LoginResponse;
import com.example.GreenNest.security.JWTTokenHelper;
import com.example.GreenNest.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
//@CrossOrigin("*")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired


    @GetMapping("/user")
    public String home(){
        return "<h1> hello </h1>";
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    //add customer
    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean insertCustomer(@RequestBody Customer customer){
        if(customer == null){
            throw new ResourceNotFoundException("Missing Data Exception");
        }
        else{
            System.out.println(customer.getProfile().getEmail());

            List<String> username = userProfileRepository.getProfile(customer.getProfile().getEmail());
            System.out.println(username);

            if(username.isEmpty()){
                customer.getProfile().setPassword(bcryptEncoder.encode(customer.getProfile().getPassword()));
                customerRepository.save(customer);
                return true;
            }else{
                System.out.println("already have an account");
                return false;
            }
        }

    }
    //add employee
    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean insertEmployee(@RequestBody Employee employee){
        if (employee == null){
            throw new ResourceNotFoundException("Missing Data Exception");
        }else{
            employeeRepository.save(employee);
        }
        return true;
    }

    //login user
//    @PostMapping(value = "/customer/login", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public UserProfile loginCustomer(@RequestBody UserProfile userProfile){
//        System.out.println(userProfile.getEmail());
//        return userProfileRepository.findByEmail(userProfile.getEmail());
//    }

    @PostMapping(value="/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println(authenticationRequest.getUserName());
        System.out.println(authenticationRequest.getPassword());
        System.out.println("*************************");
//        try{
////            authenticationManager.authenticate(
////                    new UsernamePasswordAuthenticationToken(authenticationRequest.getNewusername(), authenticationRequest.getUserpassword())
////            );
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
//            );
//            if(authentication.isAuthenticated()){
//                final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());
//
//                System.out.println(userDetails.getUsername());
//                final String jwtToken = jwtTokenHelper.generateToken(userDetails);
//                LoginResponse response = new LoginResponse();
//                response.setToken(jwtToken);
//                return  ResponseEntity.ok(response);
//            }
//
//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        }catch (BadCredentialsException e){
//           throw new Exception("incorrect username and password", e);
//        }
//
//        System.out.println("close");
//        return null;
//

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));

        System.out.println(authenticationRequest.getUserName());
        System.out.println(authenticationRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserProfile userProfile = (UserProfile)authentication.getPrincipal();
        String jwtToken = jwtTokenHelper.generateToken(userProfile.getUsername());

        int x = userProfile.getUser_id();

        Optional<Customer> customer = customerRepository.findById(x);
        Object[] roles = customer.get().getProfile().getAuthorities().toArray();
        System.out.println(customer.get().getFirst_name());
        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        List<String> role = customer.get().getProfile().getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        System.out.println(role);
        response.setRoles(role);
        response.setName(customer.get().getFirst_name());

        return  ResponseEntity.ok(response);



    }



}
