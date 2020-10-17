package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.model.Company;
import com.ischoolbar.programmer.model.Page;
import com.ischoolbar.programmer.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao extends BaseDao{
    public boolean addCompany(Company company){
        String sql = "insert into company values(null,'"+company.getName()+"','"+company.getManager()+"'";
        sql += ",'" + company.getTele() + "'," + company.getEmail();
        sql += ",'" + company.getAddress() + "','" + company.getIntro() + "'";
        sql += ",'null','" + company.getMoney() + "'";
        sql += ",'" + company.getDate() + "','" + company.getEndData() + "')";
        return update(sql);
    }
    public boolean editCompany(Company company) {
        // TODO Auto-generated method stub
        String sql = "update company set name = '"+company.getName()+"'";
        sql += ",manager = '" + company.getManager() + "'";
        sql += ",tele = '" + company.getTele() + "'";
        sql += ",email = '" + company.getEmail() + "'";
        sql += ",address = '" + company.getAddress() + "'";
        sql += ",intro = '" + company.getIntro() + "'";
        sql += ",money = " + company.getMoney();
        sql += ",date = '" + company.getDate() + "'";
        sql += ",endData = '" + company.getEndData() + "'";
        sql += " where id = " + company.getId();
        return update(sql);
    }
    public boolean setCompanyPhoto(Company company) {
        // TODO Auto-generated method stub
        String sql = "update company set icon = ? where id = ?";
        Connection connection = getConnection();
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setBinaryStream(1, company.getIcon());
            prepareStatement.setInt(2, company.getId());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return update(sql);
    }
    public boolean deleteCompany(String ids) {
        // TODO Auto-generated method stub
        String sql = "delete from company where id in("+ids+")";
        return update(sql);
    }
    public Company getCompany(int id){
        String sql = "select * from company where id = " + id;
        Company company = null;
        ResultSet resultSet = query(sql);
        try {
            if(resultSet.next()){
                company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setName(resultSet.getString("name"));
                company.setManager(resultSet.getString("manager"));
                company.setTele(resultSet.getString("tele"));
                company.setEmail(resultSet.getString("email"));
                company.setIcon(resultSet.getBinaryStream("icon"));
                company.setAddress(resultSet.getString("address"));
                company.setIntro(resultSet.getString("intro"));
                company.setMoney(resultSet.getDouble("money"));
                return company;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return company;
    }
    public List<Company> getCompanyList(Company company, Page page){
        List<Company> ret = new ArrayList<Company>();
        String sql = "select * from company ";
        if(!StringUtil.isEmpty(company.getName())){
            sql += "and name like '%" + company.getName() + "%'";
        }
        if(company.getId() != 0){
            sql += " and id = " + company.getId();
        }
        sql += " limit " + page.getStart() + "," + page.getPageSize();
        ResultSet resultSet = query(sql.replaceFirst("and", "where"));
        try {
            while(resultSet.next()){
                Company com = new Company();
                com.setId(resultSet.getInt("id"));
                com.setName(resultSet.getString("name"));
                com.setManager(resultSet.getString("manager"));
                com.setTele(resultSet.getString("tele"));
                com.setEmail(resultSet.getString("email"));
                com.setIcon(resultSet.getBinaryStream("icon"));
                com.setAddress(resultSet.getString("address"));
                com.setIntro(resultSet.getString("intro"));
                com.setMoney(resultSet.getDouble("money"));
                com.setDate(resultSet.getString("date"));
                com.setEndData(resultSet.getString("endData"));
                ret.add(com);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }
    public int getCompanyListTotal(Company company){
        int total = 0;
        String sql = "select count(*)as total from company ";
        if(!StringUtil.isEmpty(company.getName())){
            sql += "and name like '%" + company.getName() + "%'";
        }
        if(company.getId() != 0){
            sql += " and id = " + company.getId();
        }
        ResultSet resultSet = query(sql.replaceFirst("and", "where"));
        try {
            while(resultSet.next()){
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return total;
    }
}
