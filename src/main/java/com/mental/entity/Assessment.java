package com.mental.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Assessment {
    private Integer id;
    private Integer userId;
    private Integer questionnaireId;
    private Integer totalScore;
    private String level;
    private Integer isWarning;
    private String answers;
    private Date createTime;

    // 关联字段
    private String userName;
    private String questionnaireTitle;
}
