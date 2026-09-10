package com.mental.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Questionnaire {
    private Integer id;
    private String title;
    private String description;
    private Integer totalScore;
    private Integer status;
    private Date createTime;

    // 关联题目列表（非数据库字段）
    private List<Question> questions;
}
