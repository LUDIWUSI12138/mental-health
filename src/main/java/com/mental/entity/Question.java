package com.mental.entity;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private Integer questionnaireId;
    private String content;
    private String type;
    private String options;
    private Integer sortOrder;
}
