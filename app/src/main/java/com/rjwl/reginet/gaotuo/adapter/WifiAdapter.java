package com.rjwl.reginet.gaotuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.entity.WifiEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class WifiAdapter extends BaseAdapter {

    private List<WifiEntity> datas;
    private Context context;
    //private String tower;
    private List<String> towerLsit;

    private List<String> wifiName;

    public WifiAdapter(List<WifiEntity> datas, Context context, List<String> towerLsit) {
        this.datas = datas;
        this.context = context;
        //this.tower = tower;
        this.towerLsit = towerLsit;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        ViewHold viewHold = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.wifi_item, viewGroup, false);
            viewHold = new ViewHold(view);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }

        viewHold.textView.setText(towerLsit.get(i));//datas.get(i).getSsid());
        return view;
    }


    class ViewHold {
        private TextView textView;
        public ViewHold(View view) {
            textView = view.findViewById(R.id.tv_wifi_item);
        }
    }

}
