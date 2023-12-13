package com.bn.finalp.mapper;

import com.bn.finalp.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("SELECT * FROM book")
    List<Book> selectAll();

    @Select("SELECT * FROM book WHERE ISBN = #{isbn}")
    Book selectByISBN(@Param("isbn") String isbn);

    @Select("SELECT ISBN FROM book LIMIT 1000")
    List<String> selectFirst1000ISBNs();

    @Select("SELECT ISBN FROM book LIMIT 100")
    List<String> selectFirst100ISBNs();

    @Select("SELECT ISBN FROM book ")
    List<String> selectAllISBNs();
    @Select("SELECT ISBN FROM book")
    List<String> getAllISBNs();

}
