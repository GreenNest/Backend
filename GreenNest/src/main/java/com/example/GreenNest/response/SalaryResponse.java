package com.example.GreenNest.response;

public class SalaryResponse {
    private String name;
    private String email;
    private String nic;
    private String address;
    private String mobile;
    private long salary;

    public SalaryResponse() {
    }

    public SalaryResponse(String name, String email, String nic, String address, String mobile, long salary) {
        this.name = name;
        this.email = email;
        this.nic = nic;
        this.address = address;
        this.mobile = mobile;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}
