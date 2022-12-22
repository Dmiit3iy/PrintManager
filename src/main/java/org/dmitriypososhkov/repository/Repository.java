package org.dmitriypososhkov.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dmitriypososhkov.model.document.Document;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Repository {
    private int c =0;
    private ArrayList<Document> documentsRepository = new ArrayList<>();

    public Repository() {
        documentLoader();
    }


    public Document getNextDocument(){
        int i =c;
        c++;
        return documentsRepository.get(i);
    }

    public void documentLoader(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            documentsRepository = objectMapper.readValue(new File("documents.json"), new TypeReference<ArrayList<Document>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
