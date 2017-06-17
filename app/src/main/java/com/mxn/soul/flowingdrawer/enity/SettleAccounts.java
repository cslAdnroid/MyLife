package com.mxn.soul.flowingdrawer.enity;

/**
 * Created by mk on 2017/6/16.
 */

public class SettleAccounts {

    private String paymentName;//付钱的人
    private String collectMoneyName;//收钱的人
    private double money;//收钱的金额

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCollectMoneyName() {
        return collectMoneyName;
    }

    public void setCollectMoneyName(String collectMoneyName) {
        this.collectMoneyName = collectMoneyName;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
}
