package com.goldai.model;

import java.time.LocalDateTime;

public class PurchaseResponse {
    private String transactionId;
    private String status;
    private String message;
    private double amountPaid;
    private double goldPurchased;
    private double pricePerGram;
    private double totalGoldOwned;
    private double totalInvestment;
    private LocalDateTime purchaseDate;

    public PurchaseResponse() {}

    public PurchaseResponse(String transactionId, String status, String message,
                           double amountPaid, double goldPurchased, double pricePerGram,
                           double totalGoldOwned, double totalInvestment, LocalDateTime purchaseDate) {
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
        this.amountPaid = amountPaid;
        this.goldPurchased = goldPurchased;
        this.pricePerGram = pricePerGram;
        this.totalGoldOwned = totalGoldOwned;
        this.totalInvestment = totalInvestment;
        this.purchaseDate = purchaseDate;
    }

    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    public double getGoldPurchased() { return goldPurchased; }
    public void setGoldPurchased(double goldPurchased) { this.goldPurchased = goldPurchased; }
    public double getPricePerGram() { return pricePerGram; }
    public void setPricePerGram(double pricePerGram) { this.pricePerGram = pricePerGram; }
    public double getTotalGoldOwned() { return totalGoldOwned; }
    public void setTotalGoldOwned(double totalGoldOwned) { this.totalGoldOwned = totalGoldOwned; }
    public double getTotalInvestment() { return totalInvestment; }
    public void setTotalInvestment(double totalInvestment) { this.totalInvestment = totalInvestment; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
}
