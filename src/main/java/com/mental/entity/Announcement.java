package com.mental.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Announcement {
    private Integer id;
    private String title;
    private String content;
    private String type;
    private Integer status;
    private Date createTime;
}
