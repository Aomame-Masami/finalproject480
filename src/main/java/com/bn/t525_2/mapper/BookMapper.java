package com.bn.t525_2.mapper;

import com.bn.t525_2.pojo.Book;

import java.util.List;

public interface BookMapper {
    List<Book> selectAll();

    Book selectById(int id);

}
