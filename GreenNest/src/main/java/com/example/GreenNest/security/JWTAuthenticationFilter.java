package com.example.GreenNest.security;

import com.example.GreenNest.service.MyUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private MyUserDetailsService myUserDetailsService;
    private JWTTokenHelper jwtTokenHelper;

    public JWTAuthenticationFilter(MyUserDetailsService myUserDetailsService, JWTTokenHelper jwtTokenHelper) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        final String authorizationHeader = request.getHeader("Authorization");
//        String username = null;
//        String jwt =null;
//
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
//            jwt = authorizationHeader.substring(7);
//            username = jwtTokenHelper.getUsernameFromToken(jwt);
//        }

        //String authToken = jwtTokenHelper.getAuthHeaderFromHeader(request);
        String authToken = jwtTokenHelper.getToken(request);
        //System.out.println(authToken);
         if(null!=authToken){
             String userName = jwtTokenHelper.getUsernameFromToken(authToken);

             if(null!=userName){
                 UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(userName);

                 if(jwtTokenHelper.validateToken(authToken, userDetails)){
                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                     authentication.setDetails(new WebAuthenticationDetails(request));
                     //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                     SecurityContextHolder.getContext().setAuthentication(authentication);
                 }
             }
         }
         filterChain.doFilter(request,response);

    }
}
