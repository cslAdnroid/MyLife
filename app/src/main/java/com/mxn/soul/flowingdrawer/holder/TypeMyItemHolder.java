package com.mxn.soul.flowingdrawer.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.enity.Order;

/**
 * Created by mk on 2017/5/31.
 */

public class TypeMyItemHolder extends TypeBaseViewHolder<Order>{
    private TextView tvUserName;
    private TextView tvShopper;
    private TextView tvContent;
    private TextView tvTime;
    private TextView tvMoney;
    public TypeMyItemHolder(View itemView) {
        super(itemView);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
        tvUserName = (TextView) itemView.findViewById(R.id.userName);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        tvShopper = (TextView) itemView.findViewById(R.id.tvShopper);
    }

    @Override
    public void bindHolder(Order baseProduct) {

    }

    @Override
    public void bindHolder(Context context, Order baseProduct) {
        tvShopper.setText(baseProduct.getShopper());
        tvTime.setText(baseProduct.getTime());
        tvUserName.setText(baseProduct.getUser().getUsername());
        tvContent.setText(baseProduct.getContent());
        tvMoney.setText(baseProduct.getMoney()+"");
    }
}
