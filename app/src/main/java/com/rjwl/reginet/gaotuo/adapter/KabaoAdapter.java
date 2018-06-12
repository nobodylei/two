package com.rjwl.reginet.gaotuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.entity.Kabao;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class KabaoAdapter extends RecyclerView.Adapter<KabaoAdapter.MyViewHolder>{

    private Context context;
    private List<Kabao> kabaoList;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    public KabaoAdapter(Context context, List<Kabao> kabaoList, OnRecyclerviewItemClickListener onRecyclerviewItemClickListener) {
        this.context = context;
        this.kabaoList = kabaoList;
        this.mOnRecyclerviewItemClickListener = onRecyclerviewItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.kabao_item, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        MyViewHolder holder1 = holder;
        holder1.itemView.setTag(position);
        TextView tvKabao = holder1.itemView.findViewById(R.id.tv_kabao);
        tvKabao.setText(kabaoList.get(position).getWifiName());

        if( mOnRecyclerviewItemClickListener!= null){

            holder1.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecyclerviewItemClickListener.onClick(v, position);
                }
            });
            holder1.itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnRecyclerviewItemClickListener.onLongClick(v, position);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return kabaoList.size();
    }


    public interface OnRecyclerviewItemClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
            /*TextView tvKabao = view.findViewById(R.id.tv_kabao);
            tvKabao.setText("1单元1号电梯");*/
        }
    }
}
