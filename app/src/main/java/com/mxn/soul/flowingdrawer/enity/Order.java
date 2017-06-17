package com.mxn.soul.flowingdrawer.enity;

import cn.bmob.v3.BmobObject;

/**
 * 这个是账单的类
 * Created by mk on 2017/5/31.
 */

public class Order extends BmobObject{
    private User user;//用户
    private String time;//时间
    private String content;//内容
    private double money;//金钱
    private String shopper;//付款者
    private String group;//组，通过设置组来区分
    private String years;//年
    private String month;//月

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getShopper() {
        return shopper;
    }

    public void setShopper(String shopper) {
        this.shopper = shopper;
    }
}
