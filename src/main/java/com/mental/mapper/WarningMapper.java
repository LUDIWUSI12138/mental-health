package com.mental.mapper;

import com.mental.entity.Warning;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface WarningMapper {
    Warning findById(@Param("id") Integer id);
    List<Warning> findAll();
    List<Warning> findByUserId(@Param("userId") Integer userId);
    List<Warning> findByStatus(@Param("status") String status);
    int insert(Warning warning);
    int updateStatus(@Param("id") Integer id, @Param("status") String status, @Param("handlerId") Integer handlerId);
    int countPending();
    int deleteByAssessmentId(@Param("assessmentId") Integer assessmentId);
}
