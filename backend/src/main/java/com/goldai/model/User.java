package com.goldai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private String phone;
    private double totalGoldOwned;
    private double totalInvestment;
    private List<GoldPurchase> purchases;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
        this.purchases = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static class GoldPurchase {
        private String transactionId;
        private double amount;
        private double grams;
        private double pricePerGram;
        private LocalDateTime purchaseDate;
        private String status;

        public GoldPurchase() {}

        public GoldPurchase(String transactionId, double amount, double grams, 
                           double pricePerGram, String status) {
            this.transactionId = transactionId;
            this.amount = amount;
            this.grams = grams;
            this.pricePerGram = pricePerGram;
            this.status = status;
            this.purchaseDate = LocalDateTime.now();
        }

        // Getters and Setters
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public double getGrams() { return grams; }
        public void setGrams(double grams) { this.grams = grams; }
        public double getPricePerGram() { return pricePerGram; }
        public void setPricePerGram(double pricePerGram) { this.pricePerGram = pricePerGram; }
        public LocalDateTime getPurchaseDate() { return purchaseDate; }
        public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    // Main class getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public double getTotalGoldOwned() { return totalGoldOwned; }
    public void setTotalGoldOwned(double totalGoldOwned) { this.totalGoldOwned = totalGoldOwned; }
    public double getTotalInvestment() { return totalInvestment; }
    public void setTotalInvestment(double totalInvestment) { this.totalInvestment = totalInvestment; }
    public List<GoldPurchase> getPurchases() { return purchases; }
    public void setPurchases(List<GoldPurchase> purchases) { this.purchases = purchases; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
