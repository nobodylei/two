package com.rjwl.reginet.gaotuo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.entity.Xiaoqu;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class XiaoquAdapter extends RecyclerView.Adapter<XiaoquAdapter.MyViewHolder>{
    private Context context;
    private List<Xiaoqu> xiaoquList;
    private KabaoAdapter kabaoAdapter;
    //private String home;
    //声明自定义的监听接口
    private KabaoAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;


    public XiaoquAdapter(Context context, List<Xiaoqu> xiaoquList) {
        this.context = context;
        this.xiaoquList = xiaoquList;
    }
    public XiaoquAdapter(Context context, List<Xiaoqu> xiaoquList,
                         KabaoAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.xiaoquList = xiaoquList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
        //this.home = home;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.xiaoqu_item, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //holder.tv_item.setText(xiaoquList.get(position));
        kabaoAdapter = new KabaoAdapter(context,xiaoquList.get(position).getTowerPowerList(), mOnRecyclerviewItemClickListener);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        holder.recyclerView.setAdapter(kabaoAdapter);

        holder.tvXiaoqu.setText(xiaoquList.get(position).getHome());
    }

    @Override
    public int getItemCount() {
        return xiaoquList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
         RecyclerView recyclerView;
         TextView tvXiaoqu;
        public MyViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.xiaoqu_recycleView);
            tvXiaoqu = view.findViewById(R.id.xiaoqu_name);
        }
    }


}
