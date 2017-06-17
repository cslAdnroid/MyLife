package com.mxn.soul.flowingdrawer.util;

import com.mxn.soul.flowingdrawer.enity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mk on 2017/6/16.
 */

public class Calculate {
    private List<Double> doubles;
    private List<Order> mLists;

    public Calculate(List<Order> mLists) {
        this.mLists = mLists;
    }

    /**
     * 算出平均值
     * @param doubles
     * @return
     */
    public double average(List<Double> doubles){
        double all = 0.0;
        double avg = 0.0;
        for (double dou : doubles){
            all += dou;
        }
        avg = all / doubles.size();
        return avg;
    }

    /**
     * 算出每个人总共花了多少钱
     * @return
     */
    public Map<String,Double> eachPay(){
        Map<String,Double> map = new HashMap<>();
        if (mLists.size() != 0){
            List<String> names = new ArrayList<String>();
            for (Order order : mLists){
                doubles.add(order.getMoney());
                if (names.size() == 0){
                    map.put(order.getShopper(),order.getMoney());
                }else {
                   for (Map.Entry<String, Double> entry :map.entrySet()){
                       if (order.getShopper().equals(entry.getKey())){
                           map.put(entry.getKey(),entry.getValue()+order.getMoney());
                       }else{
                           map.put(order.getShopper(),order.getMoney());
                       }
                   }
                }
            }
        }
        return map;
    }

    /**
     * 根据实际的帐单来算出谁应该给谁多少钱
     * @return
     */
    public Map<String,Double> getPay(){
        Map<String, Double> map = eachPay();
        double ave = average(doubles);
        for (Map.Entry<String,Double> entry : map.entrySet()){
            double money = ave - entry.getValue();
            map.put(entry.getKey(),money);
        }
        //日后完善谁给谁多少
        return map;
    }
    public static void mian(String[] args){

    }


}
