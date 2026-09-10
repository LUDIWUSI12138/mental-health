package com.mental.mapper;

import com.mental.entity.Assessment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AssessmentMapper {
    Assessment findById(@Param("id") Integer id);
    List<Assessment> findByUserId(@Param("userId") Integer userId);
    List<Assessment> findAll();
    List<Assessment> findWarningList();
    int insert(Assessment assessment);
    int countAll();
    int countWarning();
    int deleteById(@Param("id") Integer id);
    int deleteByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
}
