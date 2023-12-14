package com.bn.finalp;

import com.bn.finalp.mapper.BookMapper;
import com.bn.finalp.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookSuggestTest {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    List<String> isbn1000;
    @BeforeEach
    public void setup() {

        isbn1000 = bookMapper.selectFirst1000ISBNs();
    }
    @Test
    public void testSuggestion() {
        int count = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            var books = bookService.suggestBooks(i, 5);
        }
        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime;
        double avgDuration = (double) totalDuration / isbn1000.size();

        System.out.println("Did "+count+" recommendations");
        System.out.println("Total query time: " + totalDuration + " nanoseconds");
        System.out.println("Average query time: " + avgDuration + " nanoseconds");
    }
}
