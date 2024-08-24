package com.chave.mapper;

import com.chave.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (username, password, salt) VALUES (#{username}, #{password}, #{salt});")
    int insertUser(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User getUserByName(String username);
}
