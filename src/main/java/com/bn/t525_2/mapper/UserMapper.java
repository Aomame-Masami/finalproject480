package com.bn.t525_2.mapper;


import com.bn.t525_2.pojo.User;
import com.bn.t525_2.util.SqlUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> selectAll();

    // 检查是否存在该用户
    User checkUser(String username);

//    User verifyUser(@Param("username")String username,
//                    @Param("password")String password);

    User verifyUser(String username, String password);

    @SelectProvider(type = SqlUtil.class, method = "loginSql")
    User login(String username, String password);



}
