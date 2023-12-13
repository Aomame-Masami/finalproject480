package com.bn.t525_2;

import com.bn.t525_2.mapper.UserMapper;
import com.bn.t525_2.pojo.User;
import com.bn.t525_2.util.JwtUtil;
import com.bn.t525_2.util.Result;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class T5252Application {



    public static void main(String[] args) throws IOException {

        SpringApplication.run(T5252Application.class,args);

//        String aUserName = "张三";
//
//        String inputN = "张三";
//        String inputP1 = "123456";
//        String inputP2 = "wrongPassword";
//
//        String uname = "新用户";
//        String pwd = "pass";
//        String tk = "asdfg";
//
//
//
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//// 有mapper就不用了
////        List<User> users = sqlSession.selectList("test.selectAll");
//
//
//        /**
//         * UserMapper接口代理对象
//         */
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        List<User> users = userMapper.selectAll();
//
////        User cUser = userMapper.checkUser(aUserName);
////        System.out.println(cUser);
////
////        User vU1 = userMapper.verifyUser(inputN, inputP1);
//////        User vU2 = userMapper.verifyUser(inputN, inputP2);
////        System.out.println(vU1);
////        System.out.println(vU2);
//
//
//
////        if (uname.equals("") || pwd.equals("")) {
////            System.out.println("用户名或密码不能为空");
////        } else {
////            User login =userMapper.login(uname, pwd, tk);
////            System.out.println(login);
////            if (login != null) {
////                System.out.println("注册成功");;
////            } else {
////                System.out.println("注册失败,用户存在");
////            }
////        }
//
//        sqlSession.close();
    }

}
