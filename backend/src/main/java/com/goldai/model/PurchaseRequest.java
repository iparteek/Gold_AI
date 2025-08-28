package com.goldai.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Email;

public class PurchaseRequest {
    @NotBlank(message = "User ID cannot be empty")
    private String userId;
    @NotBlank(message = "User name cannot be empty")
    private String userName;
    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Phone number cannot be empty")
    private String phone;
    @Positive(message = "Amount must be positive")
    private double amount;
    private double grams;

    public PurchaseRequest() {}

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public double getGrams() { return grams; }
    public void setGrams(double grams) { this.grams = grams; }
}
