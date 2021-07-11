package com.example.GreenNest.repository;

import com.example.GreenNest.model.UserProfile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {


    public UserProfile findByEmail(String name);
}
