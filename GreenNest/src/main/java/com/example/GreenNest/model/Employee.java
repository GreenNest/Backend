package com.example.GreenNest.model;

import javax.persistence.*;

@Entity
@Table(name = "worker_details")
public class Employee {

    @Id
    @Column(name = "nic")
    String nic;

    @Column(name = "first_name")
    String first_name;

    @Column(name = "last_name")
     String last_name;

    @Column(name = "address")
    String address;

    @Column(name = "mobile")
     String mobile;

    @Column(name = "account_status")
    int account_status;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    UserProfile userProfile;

    public Employee() {
    }

    public Employee(String nic, String first_name, String last_name, String address, String mobile, int account_status, UserProfile userProfile) {
        this.nic = nic;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.mobile = mobile;
        this.account_status = account_status;
        this.userProfile = userProfile;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAccount_status() {
        return account_status;
    }

    public void setAccount_status(int account_status) {
        this.account_status = account_status;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
