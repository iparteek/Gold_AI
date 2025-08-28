package com.goldai.controller;

import com.goldai.model.PurchaseRequest;
import com.goldai.model.PurchaseResponse;
import com.goldai.model.User;
import com.goldai.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/purchase")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/gold")
    public Mono<ResponseEntity<PurchaseResponse>> purchaseGold(@Valid @RequestBody PurchaseRequest request) {
        return purchaseService.processPurchase(request)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError()
                    .body(new PurchaseResponse("ERROR", "FAILED", 
                                             "Purchase failed. Please try again.", 
                                             0, 0, 0, 0, 0, null)));
    }

    @GetMapping("/user/{email}")
    public Mono<ResponseEntity<User>> getUserDetails(@PathVariable String email) {
        return purchaseService.getUserByEmail(email)
                .filter(user -> user != null)
                .map(user -> ResponseEntity.ok(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Purchase API is running successfully! 💰");
    }
}
