package com.example.genAi.util;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResumeDocumentReader {

    public List<Document> readResumeDocument(){
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader("classpath:/resume.pdf");
        return pdfReader.read();
    }
}
