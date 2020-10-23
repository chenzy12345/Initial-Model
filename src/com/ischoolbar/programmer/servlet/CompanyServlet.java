package com.ischoolbar.programmer.servlet;

import com.ischoolbar.programmer.dao.CompanyDao;
import com.ischoolbar.programmer.model.Company;
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
 * 农业公司管理后端
 */
public class CompanyServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String method = request.getParameter("method");
        if("toCompanyListView".equals(method)){
            companyList(request,response);
        }else if("AddCompany".equals(method)){
            addCompany(request,response);
        }else if("CompanyList".equals(method)){
            getCompanyList(request,response);
        }else if("EditCompany".equals(method)){
            editCompany(request,response);
        }else if("DeleteCompany".equals(method)){
            deleteCompany(request,response);
        }
    }
    private void deleteCompany(HttpServletRequest request,
                               HttpServletResponse response) {
        // TODO Auto-generated method stub
        String[] ids = request.getParameterValues("ids[]");
        String idStr = "";
        for(String id : ids){
            idStr += id + ",";
        }
        idStr = idStr.substring(0, idStr.length()-1);
        CompanyDao companyDao = new CompanyDao();
        if(companyDao.deleteCompany(idStr)){
            try {
                response.getWriter().write("success");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                companyDao.closeCon();
            }
        }
    }
    private void editCompany(HttpServletRequest request,
                             HttpServletResponse response) {
        // TODO Auto-generated method stub
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String manager = request.getParameter("manager");
        String tele = request.getParameter("tele");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String intro = request.getParameter("intro");
        double money = Double.parseDouble(request.getParameter("money"));
        String date = request.getParameter("date");
        String endDate = request.getParameter("endData");
        String type = request.getParameter("type");

        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setManager(manager);
        company.setTele(tele);
        company.setEmail(email);
        company.setAddress(address);
        company.setIntro(intro);
        company.setMoney(money);
        company.setDate(date);
        company.setEndData(endDate);
        company.setType(type);
        CompanyDao companyDao = new CompanyDao();
        if(companyDao.editCompany(company)){
            try {
                response.getWriter().write("success");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                companyDao.closeCon();
            }
        }
    }
    private void getCompanyList(HttpServletRequest request,
                                HttpServletResponse response) {
        // TODO Auto-generated method stub
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
        Company company = new Company();
        company.setName(name);
        company.setType(type);
        CompanyDao companyDao = new CompanyDao();
        List<Company> companyList = companyDao.getCompanyList(company,new Page(currentPage,pageSize));
        int total = companyDao.getCompanyListTotal(company);
        companyDao.closeCon();
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("total", total);
        ret.put("rows",companyList);
        try {
            String from = request.getParameter("from");
            if("combox".equals(from)){
                response.getWriter().write(JSONArray.fromObject(companyList).toString());
            }else{
                response.getWriter().write(JSONObject.fromObject(ret).toString());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void addCompany(HttpServletRequest request,
                            HttpServletResponse response) {
        // TODO Auto-generated method stub
        String name = request.getParameter("name");
        String manager = request.getParameter("manager");
        String tele = request.getParameter("tele");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String intro = request.getParameter("intro");
        double money = Double.parseDouble(request.getParameter("money"));
        String date = request.getParameter("date");
        String endDate = request.getParameter("endData");
        String type = request.getParameter("type");


        Company company = new Company();
        company.setName(name);
        company.setManager(manager);
        company.setTele(tele);
        company.setEmail(email);
        company.setAddress(address);
        company.setIntro(intro);
        company.setMoney(money);
        company.setType(type);
        company.setDate(date);
        company.setEndData(endDate);


        CompanyDao companyDao = new CompanyDao();
        if(companyDao.addCompany(company)){
            try {
                response.getWriter().write("success");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                companyDao.closeCon();
            }
        }
    }
    private void companyList(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        try {
            request.getRequestDispatcher("view/companyList.jsp").forward(request, response);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
