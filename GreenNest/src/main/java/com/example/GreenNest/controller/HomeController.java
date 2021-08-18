package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.Employee;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.CustomerRepository;
import com.example.GreenNest.repository.EmployeeRepository;
import com.example.GreenNest.repository.ProductRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import com.example.GreenNest.request.AuthenticationRequest;
import com.example.GreenNest.request.LoginResponse;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.security.JWTTokenHelper;
import com.example.GreenNest.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
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
    private ProductRepository productRepository;


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

    //login user
//    @PostMapping(value = "/customer/login", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public UserProfile loginCustomer(@RequestBody UserProfile userProfile){
//        System.out.println(userProfile.getEmail());
//        return userProfileRepository.findByEmail(userProfile.getEmail());
//    }

    @PostMapping(value="/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

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
     //delete user
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Customer> deleteCountry(@PathVariable("id") int id){
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //find by id
//    @GetMapping(value = "/find/{id}")
//    public ResponseEntity<Customer> getCountry(@PathVariable("id") int id){
//        Customer customer = customerRepository.findById(id);
//        return new ResponseEntity<>(customer, HttpStatus.OK);
//    }

    //add product details
    @PostMapping(value = "/add/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addProduct(@ModelAttribute ProductDetails productDetails) throws IOException {
        //System.out.println(productDetails.isIsfeatured());
        try {
            Product product = new Product();
            product.setProduct_name(productDetails.getName());
            product.setDescription(productDetails.getDetails());
            product.setPrice(productDetails.getPrice());
            product.setQuantity(productDetails.getAmount());
            product.setFeatured(productDetails.isIsfeatured());
            product.setReorder_level(productDetails.getReorderLevel());
            product.setContent(productDetails.getFiles()[0].getBytes());

            productRepository.save(product);
            return ResponseHandle.response("successfully added data", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") long id){
        Optional<Product> product = productRepository.findById(id);
        byte[] image = product.get().getContent();
        String encodeImage = Base64.getEncoder().encodeToString(image);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(encodeImage);
        //return ResponseEntity.ok().body(image);
    }

    //get product by id
//    @GetMapping(value = "/get/product/{id}")
//    public void getImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
//        Optional<Product> product = productRepository.findById(id);
//        response.setContentType("image/jpeg,image/jpg,image/png,image/gif");
//        response.getOutputStream().write(product.get().getContent());
//        response.getOutputStream().close();
//
//    }

    //get featured product
    @GetMapping(value = "/get/featured/{feature}")
    public ResponseEntity<?> getFeaturedProduct(@PathVariable("feature") Boolean feature, HttpServletResponse response) throws IOException {
        List<Product> product = productRepository.findByFeatured(feature);
        ArrayList<ProductResponse> productResponses = new ArrayList<ProductResponse>();
        for(Product x: product){
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(x.getProduct_id());
            productResponse.setName(x.getProduct_name());
            productResponse.setDescription(x.getDescription());
            productResponse.setPrice(x.getPrice());
            productResponse.setAmount(x.getQuantity());
            productResponse.setFileName(Base64.getEncoder().encodeToString(x.getContent()));
            productResponses.add(productResponse);
        }
        return ResponseEntity.ok().body(productResponses);
//        response.setContentType("image/jpeg,image/jpg,image/png,image/gif");
//        for(Product x: product){
//            response.getOutputStream().write(x.getContent());
//        }
//        response.getOutputStream().close();

    }






}
