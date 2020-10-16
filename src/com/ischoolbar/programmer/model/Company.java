package com.ischoolbar.programmer.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 农业企业实体类
 */

public class Company {
    //实体类id
    private int id;

    //企业名
    private String name;

    //企业管理者
    private String manager;

    //企业主要人员  一对多
    private List<String> mainPerson;

    //企业电话
    private String tele;

    //企业邮箱
    private String email;

    //企业地址
    private String address;

    //企业简介
    private String intro;

    //企业图标
    private InputStream icon;

    //企业注册资金
    private double money;

    //企业服务类型  一对多
    private List<String> types;

    //企业成立时间
    private String date;

    //企业营业期限到期时间
    private String endData;

    public Company() {
        this.types = new ArrayList<>();
        this.mainPerson = new ArrayList<>();
    }

    public Company(int id, String name, String manager, List<String> mainPerson, String tele, String email, String address, String intro, InputStream icon, double money, List<String> types, String date, String endData) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.mainPerson = mainPerson;
        this.tele = tele;
        this.email = email;
        this.address = address;
        this.intro = intro;
        this.icon = icon;
        this.money = money;
        this.types = types;
        this.date = date;
        this.endData = endData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<String> getMainPerson() {
        return mainPerson;
    }

    public void setMainPerson(List<String> mainPerson) {
        this.mainPerson = mainPerson;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public InputStream getIcon() {
        return icon;
    }

    public void setIcon(InputStream icon) {
        this.icon = icon;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<String> getType() {
        return types;
    }

    public void setType(List<String> type) {
        this.types = types;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manager='" + manager + '\'' +
                ", mainPerson=" + mainPerson +
                ", tele='" + tele + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", intro='" + intro + '\'' +
                ", icon='" + icon + '\'' +
                ", money=" + money +
                ", type='" + types + '\'' +
                ", date='" + date + '\'' +
                ", endData='" + endData + '\'' +
                '}';
    }
}
