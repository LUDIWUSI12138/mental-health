package com.mental.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String gender;
    private Integer age;
    private String college;
    private String major;
    private String phone;
    private String email;
    private String role;
    private Date createTime;
    private Date updateTime;
}
