package com.example.GreenNest.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave_request")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    private long id;

    @Column(name = "from_date", columnDefinition = "DATE")
    private String fromDate;

    @Column(name = "to_date", columnDefinition = "DATE")
    private String toDate;

    @Column(name="reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "approve_status", columnDefinition = "integer default 0")
    private int approve;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public LeaveRequest() {
    }

    public LeaveRequest(String fromDate, String toDate, String reason, int approve) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.approve = approve;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
