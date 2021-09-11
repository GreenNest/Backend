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
    private ProductRepository productRepository;

    @Autowired
    private OrderRequestRepository orderRequestRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


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
    public ResponseEntity<?> insertCustomer(@RequestBody Customer customer){
        if(customer == null){
            throw new ResourceNotFoundException("Missing Data Exception");
        }
        else{
            List<String> username = userProfileRepository.getProfile(customer.getProfile().getEmail());
            System.out.println(username);

            if(username.isEmpty()){
                customer.getProfile().setPassword(bcryptEncoder.encode(customer.getProfile().getPassword()));
                customerRepository.save(customer);
                return ResponseEntity.ok("Successfully create an account");
            }else{
                return ResponseEntity.badRequest().body("Email is already in use.");
            }
        }

    }

    @PostMapping(value="/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));

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

    //get the orders
    @GetMapping(value = "/order/get/{id}")
    public ResponseEntity<Object> getOrderList(@PathVariable("id") int id){
        try{
            Optional<Customer> customer = customerRepository.findById(id);
            List<OrderDetails> orderDetails = orderDetailsRepository.findByCustomer(customer.get());
            if(orderDetails.isEmpty()){
                return ResponseHandle.response("Order history is empty", HttpStatus.OK, null);
            }else {
                return ResponseHandle.response("successfully get the orders", HttpStatus.OK, orderDetails);
            }


        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
