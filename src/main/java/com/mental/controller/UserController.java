package com.mental.controller;

import com.mental.entity.User;
import com.mental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /** 登录页 */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /** 登录处理 */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
        session.setAttribute("user", user);
        if ("admin".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/student/dashboard";
    }

    /** 注册页 */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /** 注册处理 */
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userService.register(user)) {
            return "redirect:/user/login";
        }
        model.addAttribute("error", "用户名已存在");
        return "register";
    }

    /** 退出登录 */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
