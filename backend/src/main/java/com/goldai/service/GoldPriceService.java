package com.goldai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GoldPriceService {

    @Value("${gold.price.fallback:5500.0}")
    private double fallbackPrice;

    public Mono<Double> getCurrentGoldPrice() {
        // Simulate realistic price fluctuation
        double variance = Math.random() * 200 - 100; // -100 to +100
        double currentPrice = fallbackPrice + variance;
        currentPrice = Math.round(currentPrice * 100.0) / 100.0;
        return Mono.just(currentPrice);
    }

    public double getCurrentGoldPriceSync() {
        double variance = Math.random() * 200 - 100;
        double currentPrice = fallbackPrice + variance;
        return Math.round(currentPrice * 100.0) / 100.0;
    }
}
