package com.bn.finalp.mapper;


import com.bn.finalp.pojo.User;
import com.bn.finalp.util.SqlUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

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
