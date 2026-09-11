package com.example.genAi.controller;

import com.example.genAi.service.ResumeChunkService;
import com.example.genAi.service.ResumeIngestionService;
import com.example.genAi.service.ResumeRetrievalService;
import com.example.genAi.util.ResumeDocumentReader;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private ResumeDocumentReader resumeDocumentReader;
    private ResumeChunkService resumeChunkService;
    private ResumeIngestionService resumeIngestionService;
    private final ResumeRetrievalService resumeRetrievalService;

    public  TestController(ResumeDocumentReader resumeDocumentReader, ResumeChunkService resumeChunkService,ResumeIngestionService resumeIngestionService,ResumeRetrievalService resumeRetrievalService) {
        this.resumeDocumentReader = resumeDocumentReader;
        this.resumeChunkService = resumeChunkService;
        this.resumeIngestionService = resumeIngestionService;
        this.resumeRetrievalService = resumeRetrievalService;
    }

    @GetMapping("/resume/test")
    public String verifyDocumentRead(){
       List<Document> docs = resumeDocumentReader.readResumeDocument();
       docs.forEach(e->{
           System.out.println("---------- DOCUMENT ----------");
           System.out.println("Text "+e.getText());
           System.out.println("Metadata "+e.getMetadata());
       });
       return "Resume processed. Documents: " + docs.size();
    }
    @GetMapping("/chunk/test")
    public String verifyChunnkData(){
        List<Document> docs = resumeChunkService.chunkResume();
        docs.forEach(e->{
            System.out.println("---------- CHUNK ----------");
            System.out.println("Text "+e.getText());
            System.out.println("Metadata "+e.getMetadata());
        });
        return "Total chunk : " + docs.size();
    }
    @GetMapping("/vectorStore/test")
    public String vectorStore(){
        resumeIngestionService.ingestResumeDocument();
        return "Document Injected into Vector";
    }

    @GetMapping("/resume/search")
    public String searchResume(
            @RequestParam(name = "q") String question) {

        List<Document> documents =
                resumeRetrievalService.getRelatedDocuments(question);

        StringBuilder result = new StringBuilder();

        for (Document document : documents) {
            result.append("---------------\n");
            System.out.println("ID: " + document.getId());
            System.out.println("Metadata: " + document.getMetadata());
            System.out.println("TEXT:");
            System.out.println(document.getText());
        }

        return result.toString();
    }
}
