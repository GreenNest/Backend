package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.*;
import com.example.GreenNest.repository.*;
import com.example.GreenNest.request.AuthenticationRequest;
import com.example.GreenNest.request.LoginResponse;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.response.CartResponse;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.response.ReviewResponse;
import com.example.GreenNest.security.JWTTokenHelper;
import com.example.GreenNest.service.CategoryService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Autowired
    private OrderRequestRepository orderRequestRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired CartRepository cartRepository;


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

        //System.out.println(authenticationRequest.getUserName());
        //System.out.println(authenticationRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserProfile userProfile = (UserProfile)authentication.getPrincipal();
        String jwtToken = jwtTokenHelper.generateToken(userProfile.getUsername());

        int x = userProfile.getUser_id();

        Optional<Customer> customer = customerRepository.findById(x);
        Object[] roles = customer.get().getProfile().getAuthorities().toArray();
        //System.out.println(customer.get().getFirst_name());
        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        List<String> role = customer.get().getProfile().getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        //System.out.println(role);
        response.setRoles(role);
        response.setName(customer.get().getFirst_name());
        response.setId(customer.get().getCustomer_id());

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
<<<<<<< HEAD
    @GetMapping(value = "/get/categories")
    public ResponseEntity<?> getAllCategories(){
        try {
            ArrayList<String> categories = categoryRepository.getCategory();
            return ResponseHandle.response("successfully get the categories.", HttpStatus.OK, categories);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
=======
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
>>>>>>> 5f1b096f88ab153ec1d9cdc134d14b0d03a69d4c

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
    //post order request
    @PostMapping(value = "/request/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addProductRequest(@RequestBody OrderRequest orderRequest){
        try{
            System.out.println(orderRequest.getProductName());
            Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomer().getCustomer_id());
            orderRequest.setCustomer(customer.get());
            orderRequestRepository.save(orderRequest);
            return ResponseHandle.response("successfully send the request", HttpStatus.OK, orderRequest);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //post product reviews
    @PostMapping(value = "/reviews/add")
    public ResponseEntity<Object> addProductReviews(@RequestBody Reviews reviews){
        try{
            Optional<Customer> customer = customerRepository.findById(reviews.getCustomer().getCustomer_id());
            Optional<Product> product = productRepository.findById(reviews.getProduct().getProduct_id());

            reviews.setCustomer(customer.get());
            reviews.setProduct(product.get());
            reviewRepository.save(reviews);
            return ResponseHandle.response("successfully send the request", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //get reviews according to the product
    @GetMapping(value = "/reviews/get/{id}")
    public ResponseEntity<Object> getReviewsWithProduct(@PathVariable("id") Long id){
        try {
            Optional<Product> product = productRepository.findById(id);
            List<Reviews> reviews = reviewRepository.findByProduct(product.get());
            List<ReviewResponse> reviewResponses = new ArrayList<>();
            for(Reviews r: reviews){
                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setReviews(r.getReview());
                reviewResponse.setRate(r.getRating());
                reviewResponse.setDate(r.getDate());
                reviewResponse.setCustomerName(r.getCustomer().getFirst_name());
                reviewResponses.add(reviewResponse);
            }
            return ResponseHandle.response("successfully send the request", HttpStatus.OK, reviewResponses);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //add to cart
    @PostMapping(value = "/cart/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addToCart(@RequestBody Cart cart){
        try{
            Optional<Customer> customer = customerRepository.findById(cart.getCustomer().getCustomer_id());
            Optional<Product> product = productRepository.findById(cart.getProduct().getProduct_id());
            System.out.println(cart.getQuantity());
            cart.setCustomer(customer.get());
            cart.setProduct(product.get());
            cartRepository.save(cart);
            return ResponseHandle.response("successfully add to the cart", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //get cart items
    @GetMapping(value = "/cart/get/{id}")
    public ResponseEntity<Object> getCartItems(@PathVariable("id") int id){
        try{
            System.out.println(id);
            Optional<Customer> customer = customerRepository.findById(id);
            List<Cart> carts = cartRepository.findByCustomer(customer.get());
            List<CartResponse> cartResponses = new ArrayList<CartResponse>();
            for(Cart c: carts){
                CartResponse cartResponse = new CartResponse();
                cartResponse.setId(c.getCartId());
                cartResponse.setPrice(c.getTotalPrice());
                cartResponse.setQuantity(c.getQuantity());
                cartResponse.setName(c.getProduct().getProduct_name());
                cartResponse.setProduct_id(c.getProduct().getProduct_id());
                cartResponses.add(cartResponse);
            }
            return ResponseHandle.response("successfully send the request", HttpStatus.OK, cartResponses);

        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //delete item in the cart
    @DeleteMapping(value = "/cart/delete/{id}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable("id") long id){
        cartRepository.deleteById(id);
        return ResponseHandle.response("successfully delete the item", HttpStatus.OK, null);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    //update the product stock quantity
    @PutMapping(value = "/product/update/{id}/{amount}")
    public Boolean updateProductStock(@PathVariable long id, @PathVariable int amount){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not exist"));
        product.setQuantity(amount);
        Product product1 = productRepository.save(product);
        return product1.getQuantity() == amount;
    }

}
