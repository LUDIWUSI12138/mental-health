package com.mental.mapper;

import com.mental.entity.Question;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface QuestionMapper {
    List<Question> findByQuestionnaireId(@Param("questionnaireId") Integer questionnaireId);
    Question findById(@Param("id") Integer id);
    int insert(Question question);
    int update(Question question);
    int deleteById(@Param("id") Integer id);
    int deleteByQuestionnaireId(@Param("questionnaireId") Integer questionnaireId);
}
