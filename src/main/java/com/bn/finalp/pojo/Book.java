package com.bn.finalp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor

@RedisHash("Book")
public class Book {
    @Id
    private String isbn;
    private String book_Title;
    private String book_Author;
    private int year_Of_Publication;
    private String publisher;
    private String image_UrlS;
    private String image_UrlM;
    private String image_UrlL;





}
