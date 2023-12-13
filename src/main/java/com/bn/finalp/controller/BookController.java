package com.bn.finalp.controller;

import com.bn.finalp.pojo.SuggestBooksRequest;
import com.bn.finalp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/suggest")
    public List<String> suggestBooks(@RequestBody SuggestBooksRequest request) {
        return bookService.suggestBooks(request.getBook_index(), request.getTop_n());
    }

}
