package com.example.genAi.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeIngestionService {

    private final SimpleVectorStore vectorStore;
    private final ResumeChunkService resumeChunkService;

    public ResumeIngestionService(SimpleVectorStore vectorStore, ResumeChunkService resumeChunkService) {
        this.vectorStore = vectorStore;
        this.resumeChunkService = resumeChunkService;
    }


    public void ingestResumeDocument() {
        List<Document> chunks = resumeChunkService.chunkResume();
        vectorStore.add(chunks);
        System.out.println("Chunks: " + chunks.size());
    }
}
