package com.ischoolbar.programmer.servlet;

import com.ischoolbar.programmer.dao.CourseDao;
import com.ischoolbar.programmer.dao.TypeDao;
import com.ischoolbar.programmer.model.CompanyType;
import com.ischoolbar.programmer.model.Course;
import com.ischoolbar.programmer.model.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业类型信息操作
 */
public class CompanyTypeServlet extends HttpServlet {
    private static final long serialVersionUID = -1991371597134855732L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String method = request.getParameter("method");
        if("AddCourse".equals(method)){
            addCourse(request,response);
        }else if("TypeList".equals(method)){
            getTypeList(request,response);
        }else if("EditCourse".equals(method)){
            editCourse(request,response);
        }else if("DeleteCourse".equals(method)){
            deleteCourse(request,response);
        }
    }
    private void deleteCourse(HttpServletRequest request,
                              HttpServletResponse response) {
        // TODO Auto-generated method stub
        String[] ids = request.getParameterValues("ids[]");
        String idStr = "";
        for(String id : ids){
            idStr += id + ",";
        }
        idStr = idStr.substring(0, idStr.length()-1);
        CourseDao courseDao = new CourseDao();
        if(courseDao.deleteCourse(idStr)){
            try {
                response.getWriter().write("success");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                courseDao.closeCon();
            }
        }
    }
    private void editCourse(HttpServletRequest request,
                            HttpServletResponse response) {
        // TODO Auto-generated method stub
        String name = request.getParameter("name");
        int teacherId = Integer.parseInt(request.getParameter("teacherid").toString());
        int maxNum = Integer.parseInt(request.getParameter("maxnum").toString());
        int id = Integer.parseInt(request.getParameter("id").toString());
        String courseDate = request.getParameter("courseDate");
        String info = request.getParameter("info");
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setTeacherId(teacherId);
        course.setInfo(info);
        course.setCourseDate(courseDate);
        course.setMaxNum(maxNum);
        CourseDao courseDao = new CourseDao();
        String msg = "error";
        if(courseDao.editCourse(course)){
            msg = "success";
        }
        try {
            response.getWriter().write(msg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            courseDao.closeCon();
        }
    }
    private void getTypeList(HttpServletRequest request,
                               HttpServletResponse response) {
        // TODO Auto-generated method stub
        TypeDao typeDao = new TypeDao();
        List<CompanyType> typeList = typeDao.getTypeList();
        typeDao.closeCon();
        response.setCharacterEncoding("UTF-8");
        try {
            String from = request.getParameter("from");
            if("combox".equals(from)) {
                response.getWriter().write(JSONArray.fromObject(typeList).toString());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void addCourse(HttpServletRequest request,
                           HttpServletResponse response) {
        // TODO Auto-generated method stub
        String name = request.getParameter("name");
        int teacherId = Integer.parseInt(request.getParameter("teacherid").toString());
        int maxNum = Integer.parseInt(request.getParameter("maxnum").toString());
        String courseDate = request.getParameter("course_date");
        String info = request.getParameter("info");
        Course course = new Course();
        course.setName(name);
        course.setTeacherId(teacherId);
        course.setInfo(info);
        course.setMaxNum(maxNum);
        course.setCourseDate(courseDate);
        CourseDao courseDao = new CourseDao();
        String msg = "error";
        if(courseDao.addCourse(course)){
            msg = "success";
        }
        try {
            response.getWriter().write(msg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            courseDao.closeCon();
        }
    }
}
