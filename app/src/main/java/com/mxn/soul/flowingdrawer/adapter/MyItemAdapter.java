package com.mxn.soul.flowingdrawer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.enity.Order;
import com.mxn.soul.flowingdrawer.holder.TypeMyItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mk on 2017/5/31.
 */

public class MyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Order> mLists = new ArrayList<Order>();
    private LayoutInflater mLayoutInflater;

    public MyItemAdapter(List<Order> mLists, Context mContext) {
        this.mLists = mLists;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    public void updateItems(List<Order> mLists) {
        this.mLists = mLists;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeMyItemHolder(mLayoutInflater.inflate(R.layout.my_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((TypeMyItemHolder)holder).bindHolder(mContext,mLists.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMyClickItemListener.clickItem(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }
    onMyClickItemListener mOnMyClickItemListener;
    public interface onMyClickItemListener{
        void clickItem(View view,int position);
    }
    public void setOnMyClickItemListener(onMyClickItemListener mOnMyClickItemListener){
        this.mOnMyClickItemListener = mOnMyClickItemListener;
    }


}
