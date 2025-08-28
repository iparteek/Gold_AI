package com.goldai.service;

import com.goldai.model.PurchaseRequest;
import com.goldai.model.PurchaseResponse;
import com.goldai.model.User;
import com.goldai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoldPriceService goldPriceService;

    public Mono<PurchaseResponse> processPurchase(PurchaseRequest request) {
        return goldPriceService.getCurrentGoldPrice()
                .flatMap(goldPrice -> {
                    // Calculate grams if not provided
                    final double finalGrams = request.getGrams() > 0 ? 
                        request.getGrams() : request.getAmount() / goldPrice;
                    final double roundedGrams = Math.round(finalGrams * 10000.0) / 10000.0;

                    // Generate transaction ID
                    final String transactionId = "GLD_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

                    // Create or update user
                    return createOrUpdateUser(request, roundedGrams, goldPrice, transactionId)
                            .map(user -> new PurchaseResponse(
                                transactionId,
                                "SUCCESS",
                                String.format("🎉 Congratulations! You have successfully purchased %.4f grams of gold for ₹%.2f", roundedGrams, request.getAmount()),
                                request.getAmount(),
                                roundedGrams,
                                goldPrice,
                                user.getTotalGoldOwned(),
                                user.getTotalInvestment(),
                                LocalDateTime.now()
                            ));
                })
                .onErrorReturn(new PurchaseResponse(
                    "ERROR_" + System.currentTimeMillis(),
                    "FAILED",
                    "❌ Purchase failed due to technical issues. Please try again later.",
                    0, 0, 0, 0, 0,
                    LocalDateTime.now()
                ));
    }

    private Mono<User> createOrUpdateUser(PurchaseRequest request, double grams, 
                                        double goldPrice, String transactionId) {
        return Mono.fromCallable(() -> {
            // Find existing user by email
            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
            User user;
            
            if (existingUser.isPresent()) {
                user = existingUser.get();
                // Update user details
                user.setUserName(request.getUserName());
                user.setPhone(request.getPhone());
            } else {
                // Create new user
                user = new User();
                user.setUserName(request.getUserName());
                user.setEmail(request.getEmail());
                user.setPhone(request.getPhone());
            }
            
            user.setUpdatedAt(LocalDateTime.now());

            // Add new purchase
            User.GoldPurchase purchase = new User.GoldPurchase(
                transactionId,
                request.getAmount(),
                grams,
                goldPrice,
                "COMPLETED"
            );
            user.getPurchases().add(purchase);

            // Update totals
            user.setTotalGoldOwned(user.getTotalGoldOwned() + grams);
            user.setTotalInvestment(user.getTotalInvestment() + request.getAmount());

            return userRepository.save(user);
        });
    }

    public Mono<User> getUserByEmail(String email) {
        return Mono.fromCallable(() -> 
            userRepository.findByEmail(email).orElse(null)
        );
    }
}
