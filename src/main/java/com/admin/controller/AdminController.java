package com.admin.controller;

import com.admin.service.AdminService;
import com.admin.vo.AdminVO;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping({"/admin/login"})
    public String login() {
        return "admin/login";
    }

    @PostMapping(value = "/admin/login")
    public String login(@RequestParam("admAcc") String admAcc,
                        @RequestParam("admPsw") String admPsw,
                        HttpSession session) {

        if (!StringUtils.hasText(admAcc)) {
            session.setAttribute("errorMsg", "帳號:請勿空白");
            return "admin/login";
        }
        if (!StringUtils.hasText(admPsw)) {
            session.setAttribute("errorMsg", "密碼:請勿空白");
            return "admin/login";
        }

        AdminVO adminVO = adminService.login(admAcc, admPsw);

        if (adminVO != null) {
            session.setAttribute("admNo", adminVO.getAdmNo());
            session.setAttribute("admAcc", adminVO.getAdmAcc());
            session.setAttribute("admName", adminVO.getAdmName());
            //session過期時間設置為7200秒 即兩小時
            //session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/listAll";
        } else {
            session.setAttribute("errorMsg", "登入失敗");
            return "admin/login";
        }
    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admNo");
        request.getSession().removeAttribute("admAcc");
        request.getSession().removeAttribute("admName");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }

}
