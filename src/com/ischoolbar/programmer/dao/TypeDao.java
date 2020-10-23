package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.model.Company;
import com.ischoolbar.programmer.model.CompanyType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeDao extends BaseDao{
    public List<CompanyType> getTypeList(){
        List<CompanyType> result = new ArrayList<>();
        String sql = "select * from c_type";
        ResultSet resultSet = query(sql);
        try {
            while (resultSet.next()){
                CompanyType type = new CompanyType();
                type.setId(resultSet.getInt("id"));
                type.setType(resultSet.getString("type"));
                result.add(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
