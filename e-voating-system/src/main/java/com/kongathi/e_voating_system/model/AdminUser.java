package com.kongathi.e_voating_system.model;

public class AdminUser {
    private String username;
    private String password;

    // Constructors
    public AdminUser() {}
    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
