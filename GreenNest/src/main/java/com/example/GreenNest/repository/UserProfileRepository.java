package com.example.GreenNest.repository;

import com.example.GreenNest.model.UserProfile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    //user login
    public UserProfile findByEmail(String name);

    @Query("SELECT u.email FROM UserProfile u WHERE u.email = ?1")
    List <String> getProfile(String email);

    //get user password
    @Query("SELECT u.user_id FROM UserProfile u WHERE u.email = ?1 and u.password =?2")
    List<String> getMatchingId(String email, String password);

    @Query("SELECT u.islog FROM UserProfile u WHERE u.user_id =?1")
    List<String>checkLoginStatusOnUser(int userId);

    @Transactional
    @Modifying
    @Query("UPDATE UserProfile u SET u.islog = ?1 WHERE u.user_id = ?2")
    public int updateLoginDetails(int log, int username);

    @Transactional
    @Modifying
    @Query("UPDATE UserProfile u SET u.islog = ?1 WHERE u.email = ?2")
    public int updateLoginDetailsWithUsername(int log, String username);


}
