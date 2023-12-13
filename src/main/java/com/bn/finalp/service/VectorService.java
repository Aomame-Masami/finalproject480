package com.bn.finalp.service;

import com.bn.finalp.mapper.BookMapper;
import com.bn.finalp.pojo.Book;
import com.bn.finalp.pojo.VectorColumn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VectorService {

    @Autowired
    BookMapper bookMapper;
    List<List<Double>> bookVectors;

    List<String> allISBN;
    @PostConstruct
    private void init() {
        loadVectorData();
        allISBN = bookMapper.getAllISBNs();
    }

    public void loadVectorData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonFilePath = "bookcsv/vector_column.json";

            // Read JSON file and convert to List of Lists
            bookVectors = mapper.readValue(
                    new File(jsonFilePath),
                    new TypeReference<List<List<Double>>>() {
                    }
            );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public List<Integer> compareSimilarity(int index) {
        Map<Integer, Double> similarityScores = new HashMap<>();

        double[] targetVector = bookVectors.get(index).stream().mapToDouble(d -> d).toArray();

        for (int i = 0; i < bookVectors.size(); i++) {
            if (i != index) {
                double[] compareVector = bookVectors.get(i).stream().mapToDouble(d -> d).toArray();
                double similarity = cosineSimilarity(targetVector, compareVector);
                similarityScores.put(i, similarity);
            }
        }

        List<Integer> sortedIndices = new ArrayList<>(similarityScores.keySet());
        sortedIndices.sort((i1, i2) -> similarityScores.get(i2).compareTo(similarityScores.get(i1)));

        return sortedIndices;
    }

    public List<Book> bookSuggestion(String ISBN, int numOfBooks){
        List<Book> bookList = new ArrayList<>();
        int bookIndex = allISBN.indexOf(ISBN);
        List<Integer> sim = compareSimilarity(bookIndex);
        for (int i = 0; i< numOfBooks; i++){
            Book rBook = bookMapper.selectByISBN(allISBN.get(sim.get(i)));
            bookList.add(rBook);

        }

        return bookList;
    }

}
