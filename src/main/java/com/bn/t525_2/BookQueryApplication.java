package com.bn.t525_2;

import com.bn.t525_2.mapper.BookMapper;
import com.bn.t525_2.mapper.UserMapper;
import com.bn.t525_2.pojo.Book;
import com.bn.t525_2.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//@SpringBootApplication
public class BookQueryApplication {

    public static void main(String[] args) throws IOException {
        int id = 1;
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        /**
         * Mapper
         */

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
        List<Book> books = bookMapper.selectAll();
        Book aBook = bookMapper.selectById(id);

        System.out.println(aBook);

        sqlSession.close();
    }
}
