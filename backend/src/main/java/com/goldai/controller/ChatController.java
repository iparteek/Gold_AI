package com.goldai.controller;

import com.goldai.model.ChatRequest;
import com.goldai.model.ChatResponse;
import com.goldai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/message")
    public Mono<ResponseEntity<ChatResponse>> processMessage(@Valid @RequestBody ChatRequest request) {
        return chatService.processMessage(request)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError()
                    .body(new ChatResponse("Sorry, I'm having trouble processing your request. Please try again.", 
                                         false, false, null, 0.0)));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Chat API is running successfully! 🚀");
    }
}
