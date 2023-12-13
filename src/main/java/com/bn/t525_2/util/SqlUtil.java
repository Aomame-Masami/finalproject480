package com.bn.t525_2.util;

import org.apache.ibatis.jdbc.SQL;

public class SqlUtil {

    public String loginSql() {
        return new SQL() {{
                SELECT("username, password");
                FROM("user");
                WHERE("username = #{username}");
                AND();
                WHERE("password = #{password}");
            }}.toString();
    }

    public String registerSql() {
        return new SQL() {{
            INSERT_INTO("user")
                    .VALUES("username, password, token","#{username}, #{password},#{token}");
        }}.toString();
    }

    public String VerifySql() {
        return new SQL(){
            {
                SELECT("*");
                FROM("user");
                WHERE("username = #{username}");
            }
        }.toString();
    }
}
