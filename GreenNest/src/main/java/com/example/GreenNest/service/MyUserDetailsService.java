package com.example.GreenNest.service;

import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserProfileRepository userProfileRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

         UserProfile userProfile = userProfileRepository.findByEmail(s);
        //System.out.println(userProfile);
        if(userProfile == null){
            throw new UsernameNotFoundException("User Not Found with userName "+ s);
        }
        return userProfile;
    }
}
