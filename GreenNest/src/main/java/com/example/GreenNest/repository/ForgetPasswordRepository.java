package com.example.GreenNest.repository;

import com.example.GreenNest.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<PasswordResetToken, Long> {
}
