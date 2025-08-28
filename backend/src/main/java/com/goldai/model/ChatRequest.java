package com.goldai.model;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {
    @NotBlank(message = "Message cannot be empty")
    private String message;
    private String userId;

    public ChatRequest() {}
    
    public ChatRequest(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
