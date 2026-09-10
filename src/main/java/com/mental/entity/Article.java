package com.mental.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Article {
    private Integer id;
    private String title;
    private String summary;
    private String content;
    private String category;
    private String coverUrl;
    private Integer viewCount;
    private Integer status;
    private Date createTime;
}
