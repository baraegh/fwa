package com.cinema.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class LoginEntry {
    private Long        id;
    private Long        userId;
    private String      ipAddress;
    private LocalDate   date;
    private LocalTime   time;

    public LoginEntry() {
    }

    public LoginEntry(Long id, Long userId, String ipAddress, LocalDate date, LocalTime time) {
        this.id = id;
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.date = date;
        this.time = time;
    }

    public LoginEntry(Long userId, String ipAddress, LocalDate date, LocalTime time) {
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.date = date;
        this.time = time;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
