package com.mxn.soul.flowingdrawer.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mk on 2017/4/6.
 */

public abstract class TypeBaseViewHolder<T> extends RecyclerView.ViewHolder{

    public TypeBaseViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(T baseProduct);
    public abstract void bindHolder(Context context, T baseProduct);

}
