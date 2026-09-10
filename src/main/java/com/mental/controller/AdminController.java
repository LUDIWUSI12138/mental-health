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
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private QuestionnaireService questionnaireService;
    @Autowired private AssessmentService assessmentService;
    @Autowired private WarningService warningService;
    @Autowired private ArticleService articleService;
    @Autowired private MoodDiaryService moodDiaryService;
    @Autowired private AppointmentService appointmentService;
    @Autowired private AnnouncementService announcementService;

    /** 管理员首页 */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/user/login";
        model.addAttribute("studentCount", userService.countStudents());
        model.addAttribute("assessmentCount", assessmentService.countAll());
        model.addAttribute("warningCount", assessmentService.countWarning());
        model.addAttribute("pendingWarningCount", warningService.countPending());
        model.addAttribute("recentWarnings", warningService.findPending());
        model.addAttribute("articleCount", articleService.countAll());
        model.addAttribute("pendingAppointmentCount", appointmentService.countPending());
        model.addAttribute("pendingAppointments", appointmentService.findPending());
        return "admin/dashboard";
    }

    // ==================== 用户管理 ====================
    @GetMapping("/user/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.findStudents());
        return "admin/user_list";
    }

    @GetMapping("/user/edit/{id}")
    public String userEdit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/user_form";
    }

    @PostMapping("/user/save")
    public String userSave(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/admin/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String userDelete(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/admin/user/list";
    }

    // ==================== 问卷管理 ====================
    @GetMapping("/questionnaire/list")
    public String questionnaireList(Model model) {
        model.addAttribute("questionnaires", questionnaireService.findAll());
        return "admin/questionnaire_list";
    }

    @GetMapping("/questionnaire/add")
    public String questionnaireAdd(Model model) {
        model.addAttribute("questionnaire", new Questionnaire());
        return "admin/questionnaire_form";
    }

    @PostMapping("/questionnaire/save")
    public String questionnaireSave(@ModelAttribute Questionnaire questionnaire) {
        questionnaireService.save(questionnaire);
        return "redirect:/admin/questionnaire/list";
    }

    @GetMapping("/questionnaire/delete/{id}")
    public String questionnaireDelete(@PathVariable Integer id) {
        questionnaireService.delete(id);
        return "redirect:/admin/questionnaire/list";
    }

    @GetMapping("/questionnaire/{id}/questions")
    public String questionList(@PathVariable Integer id, Model model) {
        Questionnaire q = questionnaireService.findById(id);
        model.addAttribute("questionnaire", q);
        model.addAttribute("questions", q != null ? q.getQuestions() : null);
        return "admin/question_list";
    }

    // ==================== 测评记录 ====================
    @GetMapping("/assessment/list")
    public String assessmentList(Model model) {
        model.addAttribute("assessments", assessmentService.findAll());
        return "admin/assessment_list";
    }

    @GetMapping("/assessment/detail/{id}")
    public String assessmentDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("assessment", assessmentService.findById(id));
        return "admin/assessment_detail";
    }

    // ==================== 预警管理 ====================
    @GetMapping("/warning/list")
    public String warningList(Model model) {
        model.addAttribute("warnings", warningService.findAll());
        return "admin/warning_list";
    }

    @GetMapping("/warning/handle/{id}")
    public String warningHandle(@PathVariable Integer id, @RequestParam String status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        warningService.handle(id, user.getId(), status);
        return "redirect:/admin/warning/list";
    }

    // ==================== 文章管理 ====================
    @GetMapping("/article/list")
    public String articleList(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "admin/article_list";
    }

    @GetMapping("/article/add")
    public String articleAdd(Model model) {
        model.addAttribute("article", new Article());
        return "admin/article_form";
    }

    @GetMapping("/article/edit/{id}")
    public String articleEdit(@PathVariable Integer id, Model model) {
        model.addAttribute("article", articleService.findById(id));
        return "admin/article_form";
    }

    @PostMapping("/article/save")
    public String articleSave(@ModelAttribute Article article) {
        articleService.save(article);
        return "redirect:/admin/article/list";
    }

    @GetMapping("/article/delete/{id}")
    public String articleDelete(@PathVariable Integer id) {
        articleService.delete(id);
        return "redirect:/admin/article/list";
    }

    // ==================== 预约管理 ====================
    @GetMapping("/appointment/list")
    public String appointmentList(Model model) {
        model.addAttribute("appointments", appointmentService.findAll());
        return "admin/appointment_list";
    }

    @PostMapping("/appointment/handle/{id}")
    @ResponseBody
    public Object appointmentHandle(@PathVariable Integer id, @RequestParam String status,
                                    @RequestParam(required = false) String adminReply) {
        boolean ok = appointmentService.updateStatus(id, status, adminReply);
        return ok ? "{\"success\":true}" : "{\"error\":\"操作失败\"}";
    }

    // ==================== 心情日记查看 ====================
    @GetMapping("/mood/list")
    public String moodList(Model model) {
        model.addAttribute("diaries", moodDiaryService.findAll());
        return "admin/mood_list";
    }

    // ==================== 公告管理 ====================
    @GetMapping("/announcement/list")
    public String announcementList(Model model) {
        model.addAttribute("announcements", announcementService.findAll());
        return "admin/announcement_list";
    }

    @GetMapping("/announcement/add")
    public String announcementAdd(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "admin/announcement_form";
    }

    @PostMapping("/announcement/save")
    public String announcementSave(@ModelAttribute Announcement announcement) {
        announcementService.save(announcement);
        return "redirect:/admin/announcement/list";
    }

    @GetMapping("/announcement/delete/{id}")
    public String announcementDelete(@PathVariable Integer id) {
        announcementService.delete(id);
        return "redirect:/admin/announcement/list";
    }
}
