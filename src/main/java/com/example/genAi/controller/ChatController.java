package com.example.genAi.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/chat/ollama")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(OllamaChatModel chatClient) {
        this.chatClient = ChatClient.create(chatClient);
    }

    @GetMapping(path = "/{query}")
    public ResponseEntity<String> searchYourQuery(@PathVariable String query){

        String response = chatClient.prompt(query).call().content();
        return  ResponseEntity.ok(response);
    }
}
