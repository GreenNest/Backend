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
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()

                .authorizeRequests((request) -> request.antMatchers("/api/v1/auth/login", "/api/v1/customer", "/api/v1/employee","/api/v1/delete/{id}", "/api/v1/add/product","/api/v1/get/featured/{feature}","/api/v1/product/{id}",
                                "/api/v1/employees","/api/v1/viewEmployees/{id}","/api/v1/deleteEmployee/{id}","/api/v1/addSupplier","/api/v1/getCategories","/api/v1/addCategory/{id}","/api/v1/getSuppliers","/api/v1//usedCategoryAdd/{id}","/api/v1/deleteSupplier/{id}").permitAll()

                //.authorizeRequests((request) -> request.antMatchers("/api/v1/auth/login", "/api/v1/customer", "/api/v1/employee","/api/v1/delete/{id}", "/api/v1/add/product","/api/v1/get/featured/{feature}","/api/v1/get/product/{id}").permitAll()


                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter(myUserDetailsService, jwtTokenHelper),
                        UsernamePasswordAuthenticationFilter.class);

         http.csrf().disable().cors().and().headers().frameOptions().disable();

//        http.cors();
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/v1/auth/login", "/api/v1/customer", "/api/v1/employee").permitAll()
//                .anyRequest().authenticated()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


//                .and()
//                .formLogin();
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint).and()
//                .authorizeRequests((request) -> request.antMatchers("/api/v1/auth/login").permitAll().antMatchers(HttpMethod.OPTIONS, "/**")
//                .permitAll().anyRequest().authenticated())
//                .addFilterBefore(new JWTAuthenticationFilter(myUserDetailsService, jwtTokenHelper), UsernamePasswordAuthenticationFilter.class);
//
//        http.csrf().disable();

    }


}
