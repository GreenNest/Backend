package com.example.GreenNest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Table(name = "AUTH_AUTHORITY")
@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ROLE_CODE")
    private String roleCode;

    public Authority() {

    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return roleCode;
    }

    public Authority(Integer id, String roleCode) {
        this.id = id;
        this.roleCode = roleCode;
    }

    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public String getRoleCode() {
        return roleCode;
    }



    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }


}
