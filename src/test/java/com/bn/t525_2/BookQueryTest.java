package com.bn.t525_2;

import com.bn.t525_2.mapper.BookMapper;
import com.bn.t525_2.pojo.Book;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BookQueryTest {

    @Test
    public void testSelectAll() throws IOException{
        String resouce = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resouce);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        List<Book> bookList = bookMapper.selectAll();

        System.out.println(bookList);

        sqlSession.close();

    }
    @Test
    public void testFindBookById() throws IOException{

        int id = 0;

        String resouce = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resouce);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        // 方法
        Book book1 = bookMapper.selectById(1);
        System.out.println(book1);

        sqlSession.close();

    }


}
