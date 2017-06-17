package com.mxn.soul.flowingdrawer.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mk on 2017/6/5.
 */

public class MyRecyclerView extends RecyclerView{

    public MyRecyclerView(Context context) {
        super(context);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

    }
}
