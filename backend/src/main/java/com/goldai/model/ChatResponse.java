package com.goldai.model;

public class ChatResponse {
    private String response;
    private boolean isGoldInvestmentQuery;
    private boolean suggestPurchase;
    private String goldFact;
    private double currentGoldPrice;

    public ChatResponse() {}

    public ChatResponse(String response, boolean isGoldInvestmentQuery, 
                       boolean suggestPurchase, String goldFact, double currentGoldPrice) {
        this.response = response;
        this.isGoldInvestmentQuery = isGoldInvestmentQuery;
        this.suggestPurchase = suggestPurchase;
        this.goldFact = goldFact;
        this.currentGoldPrice = currentGoldPrice;
    }

    // Getters and Setters
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    public boolean isGoldInvestmentQuery() { return isGoldInvestmentQuery; }
    public void setGoldInvestmentQuery(boolean goldInvestmentQuery) { isGoldInvestmentQuery = goldInvestmentQuery; }
    public boolean isSuggestPurchase() { return suggestPurchase; }
    public void setSuggestPurchase(boolean suggestPurchase) { this.suggestPurchase = suggestPurchase; }
    public String getGoldFact() { return goldFact; }
    public void setGoldFact(String goldFact) { this.goldFact = goldFact; }
    public double getCurrentGoldPrice() { return currentGoldPrice; }
    public void setCurrentGoldPrice(double currentGoldPrice) { this.currentGoldPrice = currentGoldPrice; }
}
