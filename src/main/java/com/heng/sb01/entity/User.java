package com.heng.sb01.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类：属性对应数据库表的字段
 * Created by hengfeihu on 2017/10/23.
 */
@Entity
@Table(name = "sy_user")
public class User extends BaseModel {
    /**
     * 登陆名
     */
    @Column(length = 150)
    public String username;

    /**
     * 登陆密码
     */
    @Column(length = 50)
    public String password;

    /**
     * 员工姓名
     */
    @Column(length = 50)
    public String empname;

    /**
     * 姓名首字母拼音
     */
    @Column(length = 30)
    public String pinyin;

    @Column(length = 50)
    public String email;

    @Column(length = 30)
    public String phonenumber;

    public Integer status;

    @Column(length = 150)
    public String picture;

    /**
     * 入职日期
     */
    public Integer entrydate;

    /**
     * 离职日期
     */
    public Integer quitdate;
}