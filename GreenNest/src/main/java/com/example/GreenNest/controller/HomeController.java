package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.*;
import com.example.GreenNest.repository.*;
import com.example.GreenNest.request.AuthenticationRequest;
import com.example.GreenNest.request.LoginResponse;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.security.JWTTokenHelper;
import com.example.GreenNest.service.CategoryService;
import com.example.GreenNest.service.MyUserDetailsService;
import com.example.GreenNest.service.ProductService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;


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
        try {
            productService.addProduct(productDetails);
            //System.out.println(productDetails.getCategories());

            return ResponseHandle.response("successfully added data", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //get product by id
    @GetMapping(value = "/get/product/{id}")
    public ResponseEntity<Object> getImage(@PathVariable("id") Long id){
        try {
            ProductResponse productResponse = productService.getSingleProduct(id);
            return ResponseHandle.response("successfully get the product", HttpStatus.OK, productResponse);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    //get featured product
    @GetMapping(value = "/get/featured/{feature}")
    public ResponseEntity<?> getFeaturedProduct(@PathVariable("feature") Boolean feature, HttpServletResponse response) throws IOException {
        List<Product> product = productRepository.findByFeatured(feature);
        ArrayList<ProductResponse> productResponses = productService.createResponse(product);
        return ResponseEntity.ok().body(productResponses);
    }

    //get all the categories
//    @GetMapping(value = "/get/categories")
//    public ResponseEntity<?> getAllCategories(){
//        try {
//             //List<Category> categories = categoryRepository.findAll();
//            ArrayList<String> categories = categoryRepository.getCategory();
//            return ResponseHandle.response("successfully get the categories.", HttpStatus.OK, categories);
//        }catch (Exception e){
//            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
//        }
//    }

    //get products by category
    @GetMapping(value = "/product/{category}")
    public ResponseEntity<Object> getProductByCategory(@PathVariable("category") String category){
        try {
            ArrayList<ProductResponse> productResponses = categoryService.getProductList(category);
            return ResponseHandle.response("successfully get the categories.", HttpStatus.OK, productResponses);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }






}
