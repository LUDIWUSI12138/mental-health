package com.mental.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Warning {
    private Integer id;
    private Integer userId;
    private Integer assessmentId;
    private String warningLevel;
    private Integer score;
    private String message;
    private String status;
    private Integer handlerId;
    private Date handleTime;
    private Date createTime;

    // 关联字段
    private String userName;
    private String questionnaireTitle;
}
