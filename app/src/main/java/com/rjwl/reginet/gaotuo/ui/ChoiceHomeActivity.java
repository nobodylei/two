package com.rjwl.reginet.gaotuo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.gaotuo.MyURL;
import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.entity.Kabao;
import com.rjwl.reginet.gaotuo.entity.Power;
import com.rjwl.reginet.gaotuo.utils.BaseActivity;
import com.rjwl.reginet.gaotuo.utils.MyHttpUtils;
import com.rjwl.reginet.gaotuo.utils.SaveOrDeletePrefrence;
import com.rjwl.reginet.gaotuo.utils.WifiAdmin;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/4.
 */

public class ChoiceHomeActivity extends BaseActivity {
    private ImageView imgBack;
    private ListView lvChoiceHome;
    private List<String> homeList;
    private ArrayAdapter<String> homeAdapter;
    private String currHome = "";
    private String currTower = "";
    private String[] datas;

    private List<String> elevatorNumbers;
    private List<String> wifiList;
    private List<String> companyIds;
    private List<Kabao> powerList;
    private List<String> powerNames;
    private List<String> powerNumbers;
    private List<String> towerList;
    private List<String> homeTowerList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_home);

        initView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hidenLoading();
            switch (msg.what) {
                case 0:

                    String errMsg = msg.obj + "";
                    Toast.makeText(ChoiceHomeActivity.this, "连接异常", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    String json = msg.obj + "";
                    Log.e("TAG1", "选择小区返回：" + json);
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(json);
                        int code = jsonObject.getInteger("code");
                        String data = jsonObject.getString("data");
                        String message = jsonObject.getString("msg");
                        if (code == 200) {
                            JSONObject jsonObject1 = JSONObject.parseObject(data);
                            String card = jsonObject1.getString("card");
                            String powes = jsonObject1.getString("sendTowerPowerList");
                            powerList = parsePower(powes);
                            //保存卡包和WiFi密码
                            savePower();
                            SaveOrDeletePrefrence.save(getApplicationContext(), "card", card);

                            //SaveOrDeletePrefrence.save(getApplicationContext(), "wifiPassword", datas[1]);
                            Intent intent = new Intent(ChoiceHomeActivity.this, MainActivity.class);
                            intent.putExtra("home", currHome);
                            intent.putExtra("tower", currTower);//设备
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ChoiceHomeActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    Toast.makeText(ChoiceHomeActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                    break;
                case 3:

                    break;
            }
        }
    };

    private void initView() {
        imgBack = findViewById(R.id.img_choice_back);
        lvChoiceHome = findViewById(R.id.lv_choice_home);

        //获得集合
        homeList = SaveOrDeletePrefrence.getList(getApplicationContext(), "home");
        elevatorNumbers = SaveOrDeletePrefrence.getList(getApplicationContext(), "elevatorNumber");
        wifiList = SaveOrDeletePrefrence.getList(getApplicationContext(), "wifi");
        companyIds = SaveOrDeletePrefrence.getList(getApplicationContext(), "companyId");
        towerList = SaveOrDeletePrefrence.getList(getApplicationContext(), "towerName");
        //homeTowerList = SaveOrDeletePrefrence.getList(getApplicationContext(), "homeAndTower");
        Log.d("homelist", "choice" + homeList);

        homeAdapter = new ArrayAdapter<String>(this, R.layout.home_item, towerList);
        lvChoiceHome.setAdapter(homeAdapter);

        powerNames = new ArrayList<>();
        powerNumbers = new ArrayList<>();//初始化集合

        lvChoiceHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currHome = homeList.get(position);//小区名
                currTower = towerList.get(position);//设备名
                //Toast.makeText(ChoiceHomeActivity.this, homeList.get(position), Toast.LENGTH_SHORT).show();
                int mId = SaveOrDeletePrefrence.lookInt(getApplicationContext(), "id");
                //Log.d("CID", "Choice的CID :" + cId);
                //if ("".equals(SaveOrDeletePrefrence.look(getApplicationContext(), "card"))) {
                showLoading();
                MyHttpUtils.okHttpGet(handler, 1, 0, MyURL.sendData
                        + "?companyId=" + companyIds.get(position) + "&elevatorNumber=" + elevatorNumbers.get(position)
                        + "&wifi=" + wifiList.get(position) + "&tel="
                        + SaveOrDeletePrefrence.look(getApplicationContext(), "phone"));
                SaveOrDeletePrefrence.save(getApplicationContext(), "wifiPassword", wifiList.get(position));
                SaveOrDeletePrefrence.save(getApplicationContext(), "wifiSsid", elevatorNumbers.get(position));
                /*} else {
                    Intent intent = new Intent(ChoiceHomeActivity.this, MainActivity.class);
                    //intent.putExtra("home", currHome);
                    startActivity(intent);
                    finish();
                }*/

                if (mId == 0) {
                    handler.sendEmptyMessage(0);
                }

                if (!WifiAdmin.isNetworkConnected(ChoiceHomeActivity.this)) {
                    handler.sendEmptyMessage(2);
                }
                //SaveOrDeletePrefrence.save(getApplicationContext(), "card", "222222221234567843210C000000000000001205010101010112300000E70B");

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveOrDeletePrefrence.delete(getApplicationContext(), "card");
                SaveOrDeletePrefrence.deleteAll(getApplicationContext());

                Intent intent = new Intent(ChoiceHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private List<Kabao> parsePower(String json) {
        return JSONObject.parseArray(json, Kabao.class);
    }

    private void savePower() {
        Kabao power = null;
        for (int i = 0; i < powerList.size(); i++) {
            power = powerList.get(i);

            powerNames.add(power.getPowerName());
            powerNumbers.add(power.getPowerNumber() + "");

        }
        Log.d("gaotuo", "powerNames:" + powerNames);
        Log.d("gaotuo", "powerNumbers:" + powerNumbers);

        SaveOrDeletePrefrence.saveList(getApplicationContext(), "powerNames", powerNames);//楼层名
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "powerNumbers", powerNumbers);
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("");
            progressDialog.setMessage("正在加载...");
            //progressDialog = ProgressDialog.show(this, "", "正在加载...");
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle("");
            progressDialog.setMessage("正在加载...");
        }
        progressDialog.show();
    }

    public void hidenLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            Log.d("TAG1", "正在加载...");
            return;
        }
        super.onBackPressed();
    }
}
