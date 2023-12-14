package com.bn.finalp;

import com.bn.finalp.mapper.BookMapper;
import com.bn.finalp.service.BookService;
import com.google.common.hash.BloomFilter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class BookQueryTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    private BloomFilter<String> bloomFilter;
    List<String> isbn1000;
    @BeforeEach
    public void setup() {
        // Logic to generate the Bloom filter from CSV
        System.out.println("Generating bloom filter with CSV");
        String csvFilePath = "bookcsv/books.csv"; // Update this path as per your environment
        bloomFilter = bookService.createBloomFilter(csvFilePath, 271380);
        System.out.println("Generated filter with CSV");

        System.out.println("Getting 1000 isbn for testing");
//        isbn1000 = bookService.getIsbn1000();
        isbn1000 = bookMapper.selectFirst1000ISBNs();
    }

    @Test
    public void testBloomQuery() {
        System.out.println("Starting to test querying books from bloom filter");
        int count = 0;
        long startTime = System.nanoTime();
        for (String isbn : isbn1000) {
            boolean exists = bookService.mightContainIsbn(bloomFilter, isbn);
            if (exists) {
                count += 1;
            }
            // Optionally handle the result of the query
        }
        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime;
        double avgDuration = (double) totalDuration / isbn1000.size();

        System.out.println("Found: "+count);
        System.out.println("Total query time: " + totalDuration + " nanoseconds");
        System.out.println("Average query time: " + avgDuration + " nanoseconds");
    }

    @Test
    public void testBloomQueryFp() {
        System.out.println("Starting to test false positive rate of the bloomfilter");

        List<String> strings = generateStrings("no", 10, 1000);
        int count = 0;
        long startTime = System.nanoTime();
        for (String isbn : strings) {
            boolean exists = bookService.mightContainIsbn(bloomFilter, isbn);
            if (exists) {
                count += 1;
            }
            // Optionally handle the result of the query
        }
        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime;
        double avgDuration = (double) totalDuration / isbn1000.size();

        double fpr = (double) count / 1000;
        System.out.println("Fpr: "+fpr);
        System.out.println("Total query time: " + totalDuration + " nanoseconds");
        System.out.println("Average query time: " + avgDuration + " nanoseconds");
    }

//    @Test
//    public void testSelectAll() throws IOException{
//        String resouce = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resouce);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
//        List<Book> bookList = bookMapper.selectAll();
//        System.out.println(bookList);
//
//        sqlSession.close();
//
//    }
    @Test
    public void testFindBookById() throws IOException{
        int count = 0;

        String resouce = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resouce);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
        long startTime = System.nanoTime();
        //
        for (String isbn : isbn1000) {
            if (bookMapper.selectByISBN(isbn) != null) {
                count += 1;
            }
        }
        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime;
        double avgDuration = (double) totalDuration / isbn1000.size();
        System.out.println("Found: "+count);
        System.out.println("Total query time: " + totalDuration + " nanoseconds");
        System.out.println("Average query time: " + avgDuration + " nanoseconds");
        sqlSession.close();

    }

    private static List<String> generateStrings(String prefix, int length, int count) {
        List<String> strings = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder(prefix);
            while (sb.length() < length) {
                sb.append((char) ('a' + random.nextInt(26)));
            }
            strings.add(sb.toString());
        }
        return strings;
    }


}
