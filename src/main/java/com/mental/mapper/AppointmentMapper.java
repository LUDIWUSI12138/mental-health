package com.mental.mapper;

import com.mental.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    Appointment findById(@Param("id") Integer id);
    List<Appointment> findByUserId(@Param("userId") Integer userId);
    List<Appointment> findAll();
    List<Appointment> findByStatus(@Param("status") String status);
    int insert(Appointment appointment);
    int updateStatus(@Param("id") Integer id, @Param("status") String status, @Param("adminReply") String adminReply);
    int countPending();
}
