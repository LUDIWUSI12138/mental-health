package com.mental.mapper;

import com.mental.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findById(@Param("id") Integer id);
    List<User> findAll();
    List<User> findByRole(@Param("role") String role);
    int insert(User user);
    int update(User user);
    int deleteById(@Param("id") Integer id);
    int countAll();
    int countByRole(@Param("role") String role);
}
