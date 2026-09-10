package com.mental.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mental.entity.Question;
import com.mental.entity.Questionnaire;
import com.mental.mapper.QuestionMapper;
import com.mental.mapper.QuestionnaireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<Questionnaire> findAll() {
        return questionnaireMapper.findAll();
    }

    public List<Questionnaire> findActive() {
        return questionnaireMapper.findActive();
    }

    public Questionnaire findById(Integer id) {
        Questionnaire q = questionnaireMapper.findById(id);
        if (q != null) {
            List<Question> questions = questionMapper.findByQuestionnaireId(id);
            q.setQuestions(questions != null ? questions : new java.util.ArrayList<>());
        }
        return q;
    }

    public boolean save(Questionnaire questionnaire) {
        if (questionnaire.getId() == null) {
            return questionnaireMapper.insert(questionnaire) > 0;
        } else {
            return questionnaireMapper.update(questionnaire) > 0;
        }
    }

    public boolean delete(Integer id) {
        questionMapper.deleteByQuestionnaireId(id);
        return questionnaireMapper.deleteById(id) > 0;
    }

    public boolean saveQuestion(Question question) {
        if (question.getId() == null) {
            return questionMapper.insert(question) > 0;
        } else {
            return questionMapper.update(question) > 0;
        }
    }

    public boolean deleteQuestion(Integer id) {
        return questionMapper.deleteById(id) > 0;
    }

    public int calculateScore(Integer questionnaireId, String answersJson) {
        JSONArray answers = JSON.parseArray(answersJson);
        if (answers == null || answers.isEmpty()) return 0;
        int totalScore = 0;
        for (int i = 0; i < answers.size(); i++) {
            totalScore += answers.getIntValue(i);
        }
        return totalScore;
    }

    public String getLevel(int score, int totalQuestions) {
        double ratio = (double) score / (totalQuestions * 5);
        if (ratio < 0.4) return "normal";
        if (ratio < 0.6) return "light";
        if (ratio < 0.8) return "moderate";
        return "severe";
    }

    public boolean isWarning(String level) {
        return "moderate".equals(level) || "severe".equals(level);
    }
}
