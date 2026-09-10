package com.mental.mapper;

import com.mental.entity.Questionnaire;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface QuestionnaireMapper {
    Questionnaire findById(@Param("id") Integer id);
    List<Questionnaire> findAll();
    List<Questionnaire> findActive();
    int insert(Questionnaire questionnaire);
    int update(Questionnaire questionnaire);
    int deleteById(@Param("id") Integer id);
}
