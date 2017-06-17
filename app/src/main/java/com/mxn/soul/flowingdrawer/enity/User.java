package com.mxn.soul.flowingdrawer.enity;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * 这个是用户表
 * Created by mk on 2017/5/31.
 */

public class User extends BmobUser{

    private boolean sex;
    private List<String> group;//组集合

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
