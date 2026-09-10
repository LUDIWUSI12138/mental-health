package com.mental.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Appointment {
    private Integer id;
    private Integer userId;
    private String counselorName;
    private Date appointmentDate;
    private String appointmentTime;
    private String reason;
    private String status;
    private String adminReply;
    private Date createTime;

    // 关联字段
    private String userName;
}
