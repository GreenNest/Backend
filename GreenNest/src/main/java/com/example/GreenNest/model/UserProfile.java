package com.example.GreenNest.model;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "profile")
public class UserProfile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int user_id;

    @Column(name = "email")
    String email;

    @Column(name = "password")
     String password;

    @Column(name = "role")
     String role;

    @Column(name = "islog")
    int islog;

    public UserProfile() {
    }

    public UserProfile(int user_id, String email, String password, String role, int islog) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.islog = islog;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIslog() {
        return islog;
    }

    public void setIslog(int islog) {
        this.islog = islog;
    }
}
