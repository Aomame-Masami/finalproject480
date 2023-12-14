package com.bn.finalp;

import com.bn.finalp.mapper.BookMapper;
import com.bn.finalp.service.VectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class VectorTest {

    @Autowired
    VectorService vectorService;

    @Autowired
    BookMapper bookMapper;
    List<String> isbn100;
    @BeforeEach
    public void setup() {

        isbn100 = bookMapper.selectFirst100ISBNs();
    }

//    @Test
//    public void deserializationTest() {
//        vectorService.loadVectorData();
//    }

    @Test
    public void recommendationTest(){

        long startTime = System.nanoTime();
        for (String isbn : isbn100) {
            vectorService.bookSuggestion(isbn, 5);
        }
        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime;
        double avgDuration = (double) totalDuration / isbn100.size();

        System.out.println("Making 1000 recommendations");
        System.out.println("Total query time: " + totalDuration + " nanoseconds");
        System.out.println("Average query time: " + avgDuration + " nanoseconds");
    }
}
