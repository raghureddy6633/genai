package com.example.genAi.service;

import com.example.genAi.util.ResumeDocumentReader;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeChunkService {

    private final ResumeDocumentReader resumeDocumentReader;
    public ResumeChunkService(ResumeDocumentReader resumeDocumentReader) {
        this.resumeDocumentReader = resumeDocumentReader;
    }
    List<Character> punctuationMarks = List.of('.', '!', '?', ';', ':','\n');
    public List<Document>  chunkResume(){
       List<Document> documents = resumeDocumentReader.readResumeDocument();
        TokenTextSplitter textSplitter =  TokenTextSplitter.builder()
                .withChunkSize(300)
                .withMinChunkSizeChars(50)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(10000)
                .withKeepSeparator(true)
                .withPunctuationMarks(punctuationMarks)
                .build();
        return textSplitter.apply(documents);
    }
}
