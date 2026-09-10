package com.mental.controller;

import com.mental.entity.*;
import com.mental.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private WarningService warningService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MoodDiaryService moodDiaryService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private UserService userService;

    /** 学生首页 */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";

        List<Assessment> myAssessments = assessmentService.findByUserId(user.getId());
        List<Warning> myWarnings = warningService.findByUserId(user.getId());
        List<MoodDiary> myMoods = moodDiaryService.findByUserId(user.getId());
        List<Announcement> announcements = announcementService.findActive();

        model.addAttribute("assessments", myAssessments);
        model.addAttribute("warnings", myWarnings);
        model.addAttribute("assessmentCount", myAssessments.size());
        model.addAttribute("warningCount", myWarnings.size());
        model.addAttribute("moodCount", myMoods.size());
        model.addAttribute("moodDiaries", myMoods.size() > 3 ? myMoods.subList(0, 3) : myMoods);
        model.addAttribute("announcements", announcements.size() > 3 ? announcements.subList(0, 3) : announcements);
        return "student/dashboard";
    }

    // ==================== 问卷与测评 ====================

    @GetMapping("/questionnaire/list")
    public String questionnaireList(Model model) {
        List<Questionnaire> list = questionnaireService.findActive();
        model.addAttribute("questionnaires", list);
        return "student/questionnaire_list";
    }

    @GetMapping("/assessment/start/{id}")
    public String startAssessment(@PathVariable Integer id, Model model) {
        Questionnaire questionnaire = questionnaireService.findById(id);
        if (questionnaire == null || questionnaire.getQuestions() == null || questionnaire.getQuestions().isEmpty()) {
            return "redirect:/student/questionnaire/list";
        }
        model.addAttribute("questionnaire", questionnaire);
        return "student/assessment";
    }

    @PostMapping("/assessment/submit")
    @ResponseBody
    public Object submitAssessment(@RequestParam Integer questionnaireId,
                                   @RequestParam String answers,
                                   HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "{\"error\":\"未登录\"}";

        try {
            Assessment result = assessmentService.submitAssessment(user.getId(), questionnaireId, answers);
            if (result == null) return "{\"error\":\"提交失败：问卷不存在\"}";

            String levelText;
            switch (result.getLevel()) {
                case "normal": levelText = "正常"; break;
                case "light": levelText = "轻度"; break;
                case "moderate": levelText = "中度"; break;
                case "severe": levelText = "重度"; break;
                default: levelText = "未知";
            }

            return "{\"score\":" + result.getTotalScore() +
                    ",\"level\":\"" + levelText +
                    "\",\"isWarning\":" + result.getIsWarning() +
                    ",\"assessmentId\":" + result.getId() + "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"提交失败：" + e.getMessage() + "\"}";
        }
    }

    @GetMapping("/assessment/result/{id}")
    public String assessmentResult(@PathVariable Integer id, HttpSession session, Model model) {
        Assessment assessment = assessmentService.findById(id);
        if (assessment == null) {
            return "redirect:/student/assessment/history";
        }
        model.addAttribute("assessment", assessment);
        return "student/result";
    }

    @GetMapping("/assessment/history")
    public String assessmentHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";
        List<Assessment> list = assessmentService.findByUserId(user.getId());
        model.addAttribute("assessments", list);
        return "student/history";
    }

    @PostMapping("/assessment/delete/{id}")
    @ResponseBody
    public Object deleteAssessment(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "{\"error\":\"未登录\"}";

        boolean ok = assessmentService.deleteByIdAndUserId(id, user.getId());
        return ok ? "{\"success\":true}" : "{\"error\":\"删除失败\"}";
    }

    // ==================== 心理知识园地 ====================

    @GetMapping("/article/list")
    public String articleList(@RequestParam(required = false) String category, Model model) {
        List<Article> articles;
        if (category != null && !category.isEmpty()) {
            articles = articleService.findByCategory(category);
        } else {
            articles = articleService.findActive();
        }
        model.addAttribute("articles", articles);
        model.addAttribute("currentCategory", category);
        return "student/article_list";
    }

    @GetMapping("/article/detail/{id}")
    public String articleDetail(@PathVariable Integer id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        return "student/article_detail";
    }

    // ==================== 心情日记 ====================

    @GetMapping("/mood/list")
    public String moodList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";
        List<MoodDiary> list = moodDiaryService.findByUserId(user.getId());
        model.addAttribute("diaries", list);
        return "student/mood_list";
    }

    @PostMapping("/mood/save")
    @ResponseBody
    public Object saveMood(@RequestParam Integer moodScore,
                           @RequestParam(required = false) String content,
                           @RequestParam(required = false) String tags,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "{\"error\":\"未登录\"}";

        MoodDiary diary = new MoodDiary();
        diary.setUserId(user.getId());
        diary.setMoodScore(moodScore);
        diary.setContent(content);
        diary.setTags(tags);
        boolean ok = moodDiaryService.save(diary);
        return ok ? "{\"success\":true,\"id\":" + diary.getId() + "}" : "{\"error\":\"保存失败\"}";
    }

    @PostMapping("/mood/delete/{id}")
    @ResponseBody
    public Object deleteMood(@PathVariable Integer id) {
        boolean ok = moodDiaryService.delete(id);
        return ok ? "{\"success\":true}" : "{\"error\":\"删除失败\"}";
    }

    // ==================== 咨询预约 ====================

    @GetMapping("/appointment/list")
    public String appointmentList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";
        List<Appointment> list = appointmentService.findByUserId(user.getId());
        model.addAttribute("appointments", list);
        return "student/appointment_list";
    }

    @GetMapping("/appointment/add")
    public String appointmentAdd(Model model) {
        return "student/appointment_form";
    }

    @PostMapping("/appointment/save")
    @ResponseBody
    public Object saveAppointment(@RequestParam String counselorName,
                                  @RequestParam String appointmentDate,
                                  @RequestParam String appointmentTime,
                                  @RequestParam(required = false) String reason,
                                  HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "{\"error\":\"未登录\"}";

        Appointment appointment = new Appointment();
        appointment.setUserId(user.getId());
        appointment.setCounselorName(counselorName);
        try {
            appointment.setAppointmentDate(java.sql.Date.valueOf(appointmentDate));
        } catch (Exception e) {
            return "{\"error\":\"日期格式错误\"}";
        }
        appointment.setAppointmentTime(appointmentTime);
        appointment.setReason(reason);
        boolean ok = appointmentService.save(appointment);
        return ok ? "{\"success\":true}" : "{\"error\":\"预约失败\"}";
    }

    // ==================== 个人中心 ====================

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";
        model.addAttribute("user", user);
        return "student/profile";
    }

    @PostMapping("/profile/update")
    @ResponseBody
    public Object updateProfile(@RequestParam(required = false) String realName,
                                @RequestParam(required = false) String gender,
                                @RequestParam(required = false) Integer age,
                                @RequestParam(required = false) String college,
                                @RequestParam(required = false) String major,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String email,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "{\"error\":\"未登录\"}";

        user.setRealName(realName);
        user.setGender(gender);
        user.setAge(age);
        user.setCollege(college);
        user.setMajor(major);
        user.setPhone(phone);
        user.setEmail(email);

        boolean ok = userService.update(user);
        if (ok) {
            session.setAttribute("user", user);
        }
        return ok ? "{\"success\":true}" : "{\"error\":\"更新失败\"}";
    }
}
