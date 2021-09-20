package com.example.GreenNest.security;

import com.example.GreenNest.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);

        //datebase auth
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        //"/api/v1/get/product/{id}",
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()

                .authorizeRequests((request) -> request.antMatchers("/api/v1/auth/login", "/api/v1/customer", "/api/v1/employee","/api/v1/delete/{id}", "/api/v1/add/product","/api/v1/get/featured/{feature}",

                                "/api/v1/get/categories","/api/v1/product/{category}","/api/v1/request/add","/api/v1/reviews/add","/api/v1/cart/add", "/api/v1/getReorderLevel", "/api/v1/updateStock/{productName}/{quantity}", "/api/v1/checkedRequest/{request_id}",
                                "/api/v1/order/get/{id}","/api/v1/orderItems/get/{id}","/api/v1/complain/add","/api/v1/customer/resetPassword","/api/v1/verificationCode/get/{email}","/api/v1/userPassword/get", "/api/v1/declineRequest/{request_id}",
                                "/api/v1/orders/cashOnDelivery","/api/v1/orderStatus/update/{id}/{status}","/api/v1/employee/salary/{type}","/api/v1/editSupplier/{id}", "/api/v1/getOrderRequests", "/api/v1/suppliersByRequest/{productName}",
                                "/api/v1/getEmployee/{id}","/api/v1/editEmployee/{nic}", "/api/v1/employees","/api/v1/viewEmployees/{id}","/api/v1/deleteEmployee/{id}","/api/v1/addSupplier","/api/v1/addCategory","/api/v1/addCategory/{id}",
                                "/api/v1/product/update/{id}/{amount}", "/api/v1/getSuppliers","/api/v1/order/get/{id}", "/api/v1/suppliersByCategory","/api/v1/supplierById/{id}","/api/v1/employees","/api/v1/viewEmployees/{id}","/api/v1/deleteEmployee/{id}","/api/v1/addSupplier","/api/v1/addCategory","/api/v1/getCategories","/api/v1/addCategory/{id}","/api/v1/product/{id}","/api/v1/deleteProduct/{id}",
                        "/api/v1/get/categories","/api/v1/product/{category}","/api/v1/request/add","/api/v1/reviews/add","/api/v1/reviews/get/{id}","/api/v1/cart/add","/api/v1/cart/get/{id}","/api/v1/cart/delete/{id}","/api/v1/getDeliveryPersons",
                        "/api/v1/product/update/{id}/{amount}","/api/v1/order/get/{id}","/api/v1/orderItems/get/{id}","/api/v1/complain/add","/api/v1/customer/resetPassword","/api/v1/verificationCode/get/{email}","/api/v1/userPassword/get", "/api/v1/getSuppliers", "/api/v1/suppliersByCategory").permitAll()
                        .antMatchers("api/v1/leave/add").hasAuthority("accountant")





                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter(myUserDetailsService, jwtTokenHelper),
                        UsernamePasswordAuthenticationFilter.class);

         http.csrf().disable().cors().and().headers().frameOptions().disable();
    }
}
