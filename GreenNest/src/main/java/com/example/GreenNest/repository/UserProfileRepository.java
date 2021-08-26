package com.example.GreenNest.repository;

import com.example.GreenNest.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

      //user login
//    public UserProfile findByEmail(String name);

    UserProfile findByEmail(String username);

    @Query("SELECT u.email FROM UserProfile u WHERE u.email = ?1")
    List <String> getProfile(String email);
}
