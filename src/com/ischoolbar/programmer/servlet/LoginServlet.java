package com.ischoolbar.programmer.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ischoolbar.programmer.dao.AdminDao;
import com.ischoolbar.programmer.dao.StudentDao;
import com.ischoolbar.programmer.dao.TeacherDao;
import com.ischoolbar.programmer.model.Admin;
import com.ischoolbar.programmer.model.Student;
import com.ischoolbar.programmer.model.Teacher;
import com.ischoolbar.programmer.util.StringUtil;

/**
 * @author llq
 * 登录验证servlet
 */
public class LoginServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -5870852067427524781L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getParameter("method");
        if ("logout".equals(method)) {
            logout(request, response);
            return;
        }
        String vcode = request.getParameter("vcode");
        String name = request.getParameter("account");
        String password = request.getParameter("password");
        String loginCpacha = request.getSession().getAttribute("loginCapcha").toString();
        if (StringUtil.isEmpty(vcode)) {
            response.getWriter().write("vcodeError");
            return;
        }
        if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
            response.getWriter().write("vcodeError");
            return;
        }
        //验证码验证通过，对比用户名密码是否正确
        String loginStatus;
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.login(name, password);
        adminDao.closeCon();
        if (admin == null) {
            response.getWriter().write("loginError");
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", admin);
        loginStatus = "loginSuccess";
        response.getWriter().write(loginStatus);

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("index.jsp");
    }
}
