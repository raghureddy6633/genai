package com.example.genAi.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/chat/ollama")
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping(path = "/query")
    public ResponseEntity<String> searchyourQuery(@RequestParam(name = "req") String req){

        String response = chatClient.prompt(req).call().content();
        return  ResponseEntity.ok(response);
    }
}
