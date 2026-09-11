package com.example.genAi.controller;

import com.example.genAi.service.ResumeRetrievalService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/chat/ollama")
public class ChatController {

    private final ChatClient chatClient;
    private final ResumeRetrievalService resumeRetrievalService;

    public ChatController(OllamaChatModel chatClient, ResumeRetrievalService resumeRetrievalService) {
        this.chatClient = ChatClient.create(chatClient);
        this.resumeRetrievalService = resumeRetrievalService;
    }

    @GetMapping(path = "/{query}")
    public ResponseEntity<String> searchYourQuery(@PathVariable String query){
       List<Document> docs = resumeRetrievalService.getRelatedDocuments(query);
       String context = docs.stream().map(Document::getText).reduce("",(existing,current)->existing+"\n\n"+current);
        String response = chatClient.prompt()
                .user("""
                    Answer the question using only the provided context.
                    Context:
                    %s
                    Question:
                    %s
                    If the answer is not present in the context,
                    say that the information is not available in the resume.
                    """.formatted(context,query)).call().content();
//                (query).call().content();
        return  ResponseEntity.ok(response);
    }
}
