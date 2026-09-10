package com.mental.entity;

import lombok.Data;
import java.util.Date;

@Data
public class MoodDiary {
    private Integer id;
    private Integer userId;
    private Integer moodScore;
    private String moodEmoji;
    private String content;
    private String tags;
    private Date createTime;

    // 关联字段
    private String userName;
}
