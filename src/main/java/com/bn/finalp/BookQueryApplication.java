package com.bn.finalp;

import com.bn.finalp.mapper.BookMapper;
import com.bn.finalp.pojo.Book;
import com.google.common.hash.BloomFilter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class BookQueryApplication {

    BloomFilter<String> bloomFilter;


    public static void main(String[] args){

        SpringApplication.run(BookQueryApplication.class, args);

    }

//    @Bean
//    public ApplicationRunner importCsvData(BookService bookService) {
//        return args -> {
//            System.out.println("Storing books into Redis!");
//            String csvFilePath = "D:\\480\\books.csv";
//            bookService.importCsvToRedis(csvFilePath);
//            System.out.println("Stored book into Redis!");
//        };
//    }



}
