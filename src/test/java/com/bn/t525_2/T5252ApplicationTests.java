package com.bn.t525_2;

import com.bn.t525_2.mapper.UserMapper;
import com.bn.t525_2.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class T5252ApplicationTests {

    @Test
    public void testVerify() throws IOException {

        String inputN = "张三";
        String inputP1 = "123456";
        String inputP2 = "wrongPassword";
//        inputN = "%" + inputN + "%";
//        inputP1 = "%" + inputP1 + "%";
//
//        Map map = new HashMap<>();
//        map.put("username", inputN);
//        map.put("password", inputP1);


        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //方法
        User vU1 = userMapper.verifyUser(inputN, inputP1);
        User vU2 = userMapper.verifyUser(inputN, inputP2);
        System.out.println(vU1);
        System.out.println(vU2);

        sqlSession.close();
    }

}
