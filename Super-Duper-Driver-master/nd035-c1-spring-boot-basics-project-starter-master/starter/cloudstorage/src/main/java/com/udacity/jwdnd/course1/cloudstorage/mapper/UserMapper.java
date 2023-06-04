package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE username = #{username}")
    User getUser(String username);

    @Select("SELECT * FROM USER WHERE userid = #{userId}")
    User getUserById(Integer userId);

    @Insert("INSERT INTO USER (username, salt, password, name) VALUES(#{username}, #{salt}, #{password}, #{Name})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}