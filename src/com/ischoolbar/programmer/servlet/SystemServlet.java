package com.ischoolbar.programmer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ischoolbar.programmer.dao.AdminDao;
import com.ischoolbar.programmer.dao.StudentDao;
import com.ischoolbar.programmer.dao.TeacherDao;
import com.ischoolbar.programmer.model.Admin;
import com.ischoolbar.programmer.model.Student;
import com.ischoolbar.programmer.model.Teacher;

/**
 * @author llq
 * 系统登录后主界面
 */
public class SystemServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -7258264317769166483L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getParameter("method");
        if ("toPersonalView".equals(method)) {
            personalView(request, response);
            return;
        } else if ("EditPasswod".equals(method)) {
            editPassword(request, response);
            return;
        }
        try {
            request.getRequestDispatcher("view/system.jsp").forward(request, response);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void editPassword(HttpServletRequest request,
                              HttpServletResponse response) {
        // TODO Auto-generated method stub
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newpassword");
        response.setCharacterEncoding("UTF-8");
        //管理员
        Admin admin = (Admin) request.getSession().getAttribute("user");
        if (!admin.getPassword().equals(password)) {
            try {
                response.getWriter().write("原密码错误！");
                return;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        AdminDao adminDao = new AdminDao();
        if (adminDao.editPassword(admin, newPassword)) {
            try {
                response.getWriter().write("success");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                adminDao.closeCon();
            }
        } else {
            try {
                response.getWriter().write("数据库修改错误");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                adminDao.closeCon();
            }
        }
    }

    private void personalView(HttpServletRequest request,
                              HttpServletResponse response) {
        // TODO Auto-generated method stub
        try {
            request.getRequestDispatcher("view/personalView.jsp").forward(request, response);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
