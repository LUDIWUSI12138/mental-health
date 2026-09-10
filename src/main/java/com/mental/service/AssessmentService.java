package com.mental.service;

import com.mental.entity.Assessment;
import com.mental.entity.Questionnaire;
import com.mental.entity.Warning;
import com.mental.mapper.AssessmentMapper;
import com.mental.mapper.WarningMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Autowired
    private WarningMapper warningMapper;

    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 提交测评
     */
    @Transactional
    public Assessment submitAssessment(Integer userId, Integer questionnaireId, String answersJson) {
        Questionnaire questionnaire = questionnaireService.findById(questionnaireId);
        if (questionnaire == null) return null;

        // 计算得分
        int totalScore = questionnaireService.calculateScore(questionnaireId, answersJson);
        int totalQuestions = questionnaire.getQuestions().size();
        String level = questionnaireService.getLevel(totalScore, totalQuestions);
        boolean isWarning = questionnaireService.isWarning(level);

        // 保存测评记录
        Assessment assessment = new Assessment();
        assessment.setUserId(userId);
        assessment.setQuestionnaireId(questionnaireId);
        assessment.setTotalScore(totalScore);
        assessment.setLevel(level);
        assessment.setIsWarning(isWarning ? 1 : 0);
        assessment.setAnswers(answersJson);
        assessmentMapper.insert(assessment);

        // 如果预警，生成预警记录
        if (isWarning) {
            Warning warning = new Warning();
            warning.setUserId(userId);
            warning.setAssessmentId(assessment.getId());
            warning.setWarningLevel(level);
            warning.setScore(totalScore);
            warning.setMessage("心理健康测评预警：" + level + "级别，得分" + totalScore + "分");
            warning.setStatus("pending");
            warningMapper.insert(warning);
        }

        return assessment;
    }

    public Assessment findById(Integer id) {
        return assessmentMapper.findById(id);
    }

    public List<Assessment> findByUserId(Integer userId) {
        return assessmentMapper.findByUserId(userId);
    }

    public List<Assessment> findAll() {
        return assessmentMapper.findAll();
    }

    public List<Assessment> findWarningList() {
        return assessmentMapper.findWarningList();
    }

    public int countAll() {
        return assessmentMapper.countAll();
    }

    public int countWarning() {
        return assessmentMapper.countWarning();
    }

    @Transactional
    public boolean delete(Integer id) {
        warningMapper.deleteByAssessmentId(id);
        return assessmentMapper.deleteById(id) > 0;
    }

    @Transactional
    public boolean deleteByIdAndUserId(Integer id, Integer userId) {
        warningMapper.deleteByAssessmentId(id);
        return assessmentMapper.deleteByIdAndUserId(id, userId) > 0;
    }
}
