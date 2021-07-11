package com.example.GreenNest.model;


import javax.persistence.*;

@Entity
@Table(name = "profile")
public class UserProfile {

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

    public UserProfile() {
    }

    public UserProfile(int user_id, String email, String password, String role) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.role = role;
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
}
