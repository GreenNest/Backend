package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.*;
import com.example.GreenNest.model.OrderDetails;
import com.example.GreenNest.repository.*;
import com.example.GreenNest.request.*;
import com.example.GreenNest.response.OrderPlaceResponse;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.security.JWTTokenHelper;
import com.example.GreenNest.service.CategoryService;
import com.example.GreenNest.service.MyUserDetailsService;
import com.example.GreenNest.service.*;
import com.example.GreenNest.response.*;
import com.example.GreenNest.security.JWTTokenHelper;
import com.example.GreenNest.service.CategoryService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@RestController
//@CrossOrigin("*")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
@EnableTransactionManagement
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

    @Autowired
    private ComplainRepository complainRepository;

    @Autowired
    private ForgetPasswordRepository forgetPasswordRepository;

    @Autowired
    private MessageSource messages;

    @Autowired
    private  Environment env;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private COService coService;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderService orderService;



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
            List<String> username = userProfileRepository.getProfile(customer.getProfile().getEmail());
            System.out.println(username);

            if(username.isEmpty()){
                customer.getProfile().setPassword(bcryptEncoder.encode(customer.getProfile().getPassword()));
                customerRepository.save(customer);
                return true;
            }else{
                return false;
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
        System.out.println(authenticationRequest.getUserName());

        int x = userProfile.getUser_id();
        List<String> roles = userProfile.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        System.out.println(roles);
        if(roles.contains("customer")){
            Customer customer = customerRepository.findByProfile(userProfile);
            LoginResponse response = new LoginResponse();
            response.setToken(jwtToken);
            List<String> role = customer.getProfile().getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());

            response.setRoles(role);
            response.setName(customer.getFirst_name());
            response.setId(customer.getCustomer_id());

            return  ResponseEntity.ok(response);
        }else{
            Employee employee = employeeRepository.findByUserProfile(userProfile);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            List<String> role1 = employee.getUserProfile().getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());
            loginResponse.setRoles(role1);
            loginResponse.setName(employee.getFirst_name());
            loginResponse.setEid(employee.getNic());

            return ResponseEntity.ok(loginResponse);
        }

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
            return ResponseHandle.response("Thank you for the review.", HttpStatus.OK, null);
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
                cartResponse.setSinglePrice(c.getProduct().getPrice());
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
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findByCustomer(customer.get());
            var result = new ArrayList<OrderDetails>();
            for(OrderDetails o: orderDetailsList){
                if(o.getDelete_status() == 0){
                    result.add(o);
                }
            }
            return ResponseHandle.response("successfully get the orders", HttpStatus.OK, result);

        }catch (Exception e){
            return ResponseHandle.response("Your order history is empty.", HttpStatus.BAD_REQUEST, null);
        }
    }

    //get order items
    @GetMapping(value = "/orderItems/get/{id}")
    public ResponseEntity<Object> getOrderItems(@PathVariable("id") long id){
        try{
            Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
            List<OrderItems> orderItems = orderItemRepository.findByOrderDetails(orderDetails.get());
            List<OrderResponse> orderResponses = new ArrayList<OrderResponse>();
            for(OrderItems o: orderItems){
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setImage(Base64.getEncoder().encodeToString(o.getProduct().getContent()));
                orderResponse.setName(o.getProduct().getProduct_name());
                orderResponse.setPrice(o.getProduct().getPrice());
                orderResponse.setQuantity(o.getQuantity());
                orderResponse.setProductId(o.getProduct().getProduct_id());
                orderResponses.add(orderResponse);

            }

            return ResponseHandle.response("", HttpStatus.OK, orderResponses);

        }catch (Exception e){
            return ResponseHandle.response("Empty order items.", HttpStatus.MULTI_STATUS, null);
        }
    }

    //add the order complain
    @PostMapping(value = "/complain/add")
    public ResponseEntity<Object> addOrderComplain(@RequestBody Complain complain){
        try {
            Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(complain.getOrderDetails().getOrder_id());
            Optional<Customer> customer = customerRepository.findById(complain.getCustomer().getCustomer_id());

            complain.setOrderDetails(orderDetails.get());
            complain.setCustomer(customer.get());
            complainRepository.save(complain);
            return ResponseHandle.response("Add your complain.", HttpStatus.OK, null);


        }catch (Exception e){
            return ResponseHandle.response("Empty order items.", HttpStatus.MULTI_STATUS, null);
        }
    }


    //reset password
    @PostMapping(value = "/customer/resetPassword", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> resetPassword(@RequestParam("email") String userEmail) {
        try {
            //System.out.println(userEmail);
            UserProfile userProfile = userProfileRepository.findByEmail(userEmail.trim());
            if(userProfile == null){
                return ResponseHandle.response("Invalid email", HttpStatus.BAD_REQUEST, null);
            }
            //System.out.println(userProfile.getEmail());
            Random rnd = new Random();
            int number = rnd.nextInt(999999) + 100000;
            //System.out.println(number);

            userProfile.setPasswordPin(number);
            userProfileRepository.save(userProfile);

            sendMail(userEmail, number);

                //mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, userProfile));
            return ResponseHandle.response("We have sent the reset password link to your email.", HttpStatus.OK, null);


        }catch ( Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    private void sendMail( String email, int resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("mecare95@gmail.com", "Technical support");
        helper.setTo(email);
        String subject = "Here is the link to reset your password";
        String content = "<p>Use this verification code "+ resetPasswordLink+" to reset your password</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    //get the OTP number
    @GetMapping(value = "/verificationCode/get/{email}")
    public ResponseEntity<Object> getVerificationCode(@PathVariable("email") String email){
        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if(userProfile != null){
            int code = userProfile.getPasswordPin();
            return ResponseHandle.response("Verification code", HttpStatus.OK, code);
        }
        return ResponseHandle.response("User not found", HttpStatus.OK, null);
    }

    //update the current userpassword
    @PutMapping(value = "/userPassword/get",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> getUserPassword(@RequestParam("password") String userPassword, @RequestParam("email")String userEmail){
        UserProfile userProfile = userProfileRepository.findByEmail(userEmail);
        if(userProfile != null){
            userProfile.setPassword(bcryptEncoder.encode(userPassword.trim()));
            //customer.getProfile().setPassword(bcryptEncoder.encode(customer.getProfile().getPassword()));
            userProfile.setPasswordPin(0);
            userProfileRepository.save(userProfile);
            return ResponseHandle.response("Reset your password. Please login.", HttpStatus.OK, null);
        }
        return ResponseHandle.response("user not found", HttpStatus.BAD_REQUEST, null);
    }

    //get the cash on delivery orders
    @GetMapping(value = "/orders/cashOnDelivery")
    public ResponseEntity<Object> getCashOnDeliveryOrders(){
        List<OrderDetails> orderDetailsList1= orderDetailsRepository.findAll();
        if(orderDetailsList1.isEmpty()){
            return ResponseHandle.response("No cash on delivery orders.", HttpStatus.BAD_REQUEST, null);
        }else{
            ArrayList<COResponse> coResponses = coService.createCOResponses(orderDetailsList1);
            if(coResponses.isEmpty()){
                return ResponseHandle.response("No cash on delivery orders.", HttpStatus.BAD_REQUEST, null);
            }
            return ResponseHandle.response("order details", HttpStatus.OK, coResponses);
        }

    }

    //update the order status
    @PutMapping(value = "/orderStatus/update/{id}/{status}")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable long id, @PathVariable boolean status){
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
        if(status){
            orderDetails.get().setOrder_status("Delivered");
            OrderDetails orderDetails1 = orderDetailsRepository.save(orderDetails.get());
            return ResponseHandle.response("Update the delivery status.", HttpStatus.OK,null);
        }

        return ResponseHandle.response("Order not found", HttpStatus.BAD_REQUEST, null);
    }

    //add leave request
    @PostMapping(value = "/leave/add")
    public ResponseEntity<Object> addLeaveRequest(@RequestBody LeaveRequest leaveRequest){
        Optional<Employee> employee = employeeRepository.findById(leaveRequest.getEmployee().getNic());
        if(employee.isPresent()){
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            //set data into variable
            final String date1 = leaveRequest.getFromDate();
            final String date2 = leaveRequest.getToDate();

            //convert into date fromat
            final LocalDate date3 = LocalDate.parse(date1 , formatter);
            final LocalDate date4 = LocalDate.parse(date2, formatter);
//            System.out.println(leaveRequest.getToDate());
//            System.out.println(date3.getMonth().getValue());

            //get the leave days
            final long  days = ChronoUnit.DAYS.between(date3, date4);
//            System.out.println(days);

            //get the year and month
            String name = date3.toString().substring(0,8);
            leaveRequest.setEmployee(employee.get());
//            System.out.println(name);

            //get the all requests related to the employee
            List<LeaveRequest>  leaveRequestList = leaveRequestRepository.findByEmployee(employee.get());
            List<LeaveRequest> leaveRequests = new ArrayList<LeaveRequest>();
            for(LeaveRequest x : leaveRequestList){
                String newDate1 = x.getFromDate();
                String newDate2 = x.getToDate();
                if(newDate1.contains(name) && newDate2.contains(name)){
                    leaveRequests.add(x);
                }
            }
            if(leaveRequests.isEmpty()){
                if(days <= 3){
                    leaveRequestRepository.save(leaveRequest);
                    return ResponseHandle.response("Send the leave request", HttpStatus.OK,null);
                }
                return ResponseHandle.response("You can get only 3 leave requests.", HttpStatus.OK,null);


            }else{
                long totalRequests = 0;
                for(LeaveRequest l : leaveRequests){
                    final LocalDate leaveDate1 = LocalDate.parse(l.getFromDate() , formatter);
                    final LocalDate leaveDate2 = LocalDate.parse(l.getToDate(), formatter);

                    long count = ChronoUnit.DAYS.between(leaveDate1, leaveDate2);
                    totalRequests = totalRequests+count;
                }
//            System.out.println("requests days "+totalRequests);
                if(totalRequests >= 3){
                    return ResponseHandle.response("You exceed the number of leave requests", HttpStatus.OK,null);
                }else{
                    if(3 - totalRequests >= days){
                        leaveRequestRepository.save(leaveRequest);
                        return ResponseHandle.response("Send the leave request", HttpStatus.OK,null);
                    }
                    long difference = 3- totalRequests;
                    String message = "You can get only " + difference + " leave requests.";
                    return ResponseHandle.response(message, HttpStatus.OK,null);
                }
            }

        }
        return ResponseHandle.response("Invalid user", HttpStatus.BAD_REQUEST,null);

    }

    //get order details by id
    @GetMapping(value = "/get/invoiceDetails/{id}")
    public ResponseEntity<Object> getOrderDetailsById(@PathVariable("id") long id){
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCustomer_name(orderDetails.get().getCustomer().getFirst_name());
        invoiceData.setDate(orderDetails.get().getDate());
        invoiceData.setAddress1(orderDetails.get().getAddress());
        invoiceData.setTown(orderDetails.get().getCity());

        List<OrderItems> orderItemsList = orderItemRepository.findByOrderDetails(orderDetails.get());
        List<OrderResponse> orderResponses = new ArrayList<OrderResponse>();
        for (OrderItems o: orderItemsList){
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setName(o.getProduct().getProduct_name());
            orderResponse.setPrice(o.getProduct().getPrice());
            orderResponse.setQuantity(o.getQuantity());
            orderResponses.add(orderResponse);
        }
        invoiceData.setOrderResponses(orderResponses);

        return ResponseHandle.response("Invalid user", HttpStatus.OK, invoiceData);


    }

    //send invoice
    @PostMapping(value = "/invoice/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> sendInvoice( @RequestParam("invoice") MultipartFile multipartFile, @RequestParam("id") long id) throws MessagingException, UnsupportedEncodingException {
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
        System.out.println(id);
        System.out.println(orderDetails.get().getCustomer().getFirst_name());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("greennest06@gmail.com", "Green Nest");
        helper.setTo(orderDetails.get().getCustomer().getProfile().getEmail());
        String subject = "Your Order Invoice";
        String content = "<p>Hi "+ orderDetails.get().getCustomer().getFirst_name() + "</p> " +
                "<p>Thanks for your order. Its on build until we confirm that payment has been received.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            InputStreamSource source = new InputStreamSource() {
                public InputStream getInputStream() throws IOException {
                    return multipartFile.getInputStream();
                }
            };
            helper.addAttachment(fileName, source );
            //helper.addInline("image001", new File(fileName));
            mailSender.send(message);
        }
        return ResponseHandle.response("Send the invoice to the customer", HttpStatus.OK, null);
    }

    //get order count of every month
    @GetMapping(value = "/order/getCount")
    public ResponseEntity<Object> getOrderCountOfMoth(){
       try{
           List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
           int count1 = 0;
           int count2 = 0;
           int count3 = 0;
           int count4 = 0;
           int count5 = 0;
           int count6 = 0;
           int count7 = 0;
           int count8 = 0;
           int count9 = 0;
           int count10 = 0;
           int count11 = 0;
           int count12 = 0;
           for(OrderDetails o : orderDetailsList){
               //System.out.println(o.getDate());
               if(o.getDate().toString().contains("-01-")){
                   count1++;
               }
               if(o.getDate().toString().contains("-02-")){
                   count2++;
               }
               if(o.getDate().toString().contains("-03-")){
                   count3++;
               }
               if(o.getDate().toString().contains("-04-")){
                   count4++;
               }
               if(o.getDate().toString().contains("-05-")){
                   count5++;
               }
               if(o.getDate().toString().contains("-06-")){
                   count6++;
               }
               if(o.getDate().toString().contains("-07-")){
                   count7++;
               }
               if(o.getDate().toString().contains("-08-")){
                   count8++;
               }
               if(o.getDate().toString().contains("-09-")){
                   count9++;
               }
               if(o.getDate().toString().contains("-10-")){
                   count10++;
               }
               if(o.getDate().toString().contains("-11-")){
                   count11++;
               }
               if(o.getDate().toString().contains("-12-")){
                   count12++;
               }

           }
           List<Integer> data = new ArrayList<Integer>();
           data.add(count1);
           data.add(count2);
           data.add(count3);
           data.add(count4);
           data.add(count5);
           data.add(count6);
           data.add(count7);
           data.add(count8);
           data.add(count9);
           data.add(count10);
           data.add(count11);
           data.add(count12);

           return ResponseHandle.response("Get the order count", HttpStatus.OK, data);
       }catch (Exception e){
           return ResponseHandle.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
       }
    }

    //get leave statistics
    @GetMapping(value = "/leave/count/{nic}")
    public ResponseEntity<Object> getTheLeaveCount(@PathVariable("nic") String nic){
        try{
            Optional<Employee> employee = employeeRepository.findById(nic);
            List<LeaveRequest> leaveRequestList = leaveRequestRepository.findByEmployee(employee.get());
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            int count5 = 0;
            int count6 = 0;
            int count7 = 0;
            int count8 = 0;
            int count9 = 0;
            int count10 = 0;
            int count11 = 0;
            int count12 = 0;
            for(LeaveRequest o : leaveRequestList){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                //set data into variable
                final String date1 = o.getFromDate();
                final String date2 = o.getToDate();

                //convert into date fromat
                final LocalDate date3 = LocalDate.parse(date1 , formatter);
                final LocalDate date4 = LocalDate.parse(date2, formatter);

                //get the leave days
                final long  days = ChronoUnit.DAYS.between(date3, date4);
                //System.out.println(o.getDate());
                if(date3.toString().contains("-01-")){
                    count1++;
                }
                if(date3.toString().contains("-02-")){
                    count2++;
                }
                if(date3.toString().contains("-03-")){
                    count3++;
                }
                if(date3.toString().contains("-04-")){
                    count4++;
                }
                if(date3.toString().contains("-05-")){
                    count5++;
                }
                if(date3.toString().contains("-06-")){
                    count6++;
                }
                if(date3.toString().contains("-07-")){
                    count7++;
                }
                if(date3.toString().contains("-08-")){
                    count8++;
                }
                if(date3.toString().contains("-09-")){
                    count9++;
                }
                if(date3.toString().contains("-10-")){
                    count10++;
                }
                if(date3.toString().contains("-11-")){
                    count11++;
                }
                if(date3.toString().contains("-12-")){
                    count12++;
                }

            }
            List<Integer> data = new ArrayList<Integer>();
            data.add(count1);
            data.add(count2);
            data.add(count3);
            data.add(count4);
            data.add(count5);
            data.add(count6);
            data.add(count7);
            data.add(count8);
            data.add(count9);
            data.add(count10);
            data.add(count11);
            data.add(count12);
            return ResponseHandle.response("Get the order count", HttpStatus.OK, data);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(value = "/customer/orderRequest/{id}")
    public ResponseEntity<Object> getCustomerOrderRequest(@PathVariable("id") int id){
        try{
            Optional<Customer> customer = customerRepository.findById(id);
            List<OrderRequest> orderRequestList = orderRequestRepository.findByCustomer(customer.get());

            List<OrderRequest> orderRequests = new ArrayList<OrderRequest>();
            for(OrderRequest o : orderRequestList){
                if(o.getCustomerDelete() == 0){
                    orderRequests.add(o);
                }
            }
            return ResponseHandle.response("Get the all requests", HttpStatus.OK, orderRequests);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @PutMapping(value = "/customer/orderRequest/delete/{id}")
    public ResponseEntity<Object> deleteOrderRequestCustomer(@PathVariable long id){
        //Optional<Customer> customer = customerRepository.findById(id);
        try{
            Optional<OrderRequest> orderRequest = orderRequestRepository.findById(id);
            orderRequest.get().setCustomerDelete(1);
            orderRequest.get().setModeratorDelete(1);
            orderRequestRepository.save(orderRequest.get());
            return ResponseHandle.response("Delete the request", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(value = "/customer/acceptRequest/{id}")
    public ResponseEntity<Object> acceptTheOrderRequest(@PathVariable long id){
        try{
            Optional<OrderRequest> orderRequest =  orderRequestRepository.findById(id);
            orderRequest.get().setCustomerAccept(1);
            orderRequest.get().setCustomerDelete(1);
            orderRequestRepository.save(orderRequest.get());

            Optional<Customer> customer = customerRepository.findById(orderRequest.get().getCustomer().getCustomer_id());
            long pid = productRepository.requestProduct(orderRequest.get().getProductName());
            System.out.println(pid);
            Optional<Product> product = productRepository.findById(pid);

            Cart cart = new Cart();
            cart.setQuantity(orderRequest.get().getQuantity());
            cart.setCustomer(customer.get());
            cart.setTotalPrice(orderRequest.get().getQuantity() * product.get().getPrice());
            cart.setProduct(product.get());
            cartRepository.save(cart);

            return ResponseHandle.response("Add to the cart", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }


    }

    @GetMapping(value = "/orderType/count")
    public ResponseEntity<Object> getTheOrderTypeCount(){
        try{
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
            ArrayList<Integer> data = new ArrayList<Integer>();
            int count1 =0;
            int count2 = 0;
            for(OrderDetails o: orderDetailsList){
                if(o.getOrder_type().contains("cash on delivery")){
                    count1++;
                }else{
                    count2++;
                }
            }
            data.add(count1);
            data.add(count2);
            return ResponseHandle.response("get the data", HttpStatus.OK, data);

        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping(value = "/order/add")
    public ResponseEntity<Object> saveTheOrderDetails(@RequestBody OrderDetails orderDetails){
        try{
            OrderDetails orderDetails1 = orderService.addOrder(orderDetails);
            //OrderDetails orderDetails1 = orderDetailsRepository.findById()
            return ResponseHandle.response("Confirm the order", HttpStatus.OK, orderDetails1.getOrder_id());

        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @PostMapping(value = "/orderItems/add/{oid}")
    public ResponseEntity<Object> addTheOrderItems(@RequestBody List<Long> id, @PathVariable long oid){
        try{

            for(int i=0 ; i<id.size(); i++){
                OrderItems orderItems = new OrderItems();
                Optional<Cart> cart = cartRepository.findById(id.get(i));
                //System.out.println(cart.get().getCartId());
                Optional<Product> product = productRepository.findById(cart.get().getProduct().getProduct_id());
                //System.out.println(product.get().getProduct_name());
                Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(oid);
                orderItems.setQuantity(cart.get().getQuantity());
                orderItems.setProduct(product.get());
                orderItems.setOrderDetails(orderDetails.get());
                orderItemRepository.save(orderItems);
                cartRepository.deleteById(cart.get().getCartId());
            }

            return ResponseHandle.response("save the items", HttpStatus.OK, null);

        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }



}
