package com.example.genAi.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeRetrievalService {

    private final SimpleVectorStore simpleVectorStore;

    public ResumeRetrievalService(SimpleVectorStore simpleVectorStore) {
        this.simpleVectorStore = simpleVectorStore;
    }

    public List<Document> getRelatedDocuments(String question){
        return simpleVectorStore.similaritySearch(SearchRequest.builder().query(question)
                .topK(5)
                .similarityThreshold(0.0)
                .build());
    }
}
