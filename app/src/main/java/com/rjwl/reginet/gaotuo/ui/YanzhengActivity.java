package com.rjwl.reginet.gaotuo.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.gaotuo.entity.User;
import com.rjwl.reginet.gaotuo.utils.BaseActivity;
import com.rjwl.reginet.gaotuo.utils.MyHttpUtils;
import com.rjwl.reginet.gaotuo.MyURL;
import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.utils.SaveOrDeletePrefrence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YanzhengActivity extends BaseActivity {

    private String phone;
    private String pw;
    private String name;

    private EditText yanzhengCode;
    private Button button;

    private List<User> userList;
    private List<String> ids;
    private List<String> companyIds;
    private List<String> homeList;
    private List<String> towerList;
    private List<String> elevatorIds;
    private List<String> elevatorNumbers;
    private List<String> wifiList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String errMsg = (String) msg.obj;
                    Toast.makeText(YanzhengActivity.this, errMsg, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    String json = (String) msg.obj;
                    Log.e("验证结果", ":" + json);

                    try {
                        JSONObject jsonObject = JSONObject.parseObject(json);
                        int code = jsonObject.getInteger("code");
                        String message = jsonObject.getString("data");
                        if ("[]".equals(message) || "".equals(message) || message == null) {
                            Toast.makeText(YanzhengActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (code == 200) {
                            userList = parseUserList(message);
                            //String token = userList.get(0).getState();
                            //int id = userList.get(0).getId();
                            name = userList.get(0).getName();

                            //SaveOrDeletePrefrence.saveInt(getApplicationContext(), "id", id);
                            SaveOrDeletePrefrence.save(getApplicationContext(), "name", name);
                            SaveOrDeletePrefrence.save(getApplicationContext(), "phone", phone);
                            SaveOrDeletePrefrence.save(getApplicationContext(), "password", pw);
                            // SaveOrDeletePrefrence.save(YanzhengActivity.this, phone, "true");
                            SaveOrDeletePrefrence.saveBoolean(getApplicationContext(), phone, true);

                            saveData();

                            Intent intent = new Intent(YanzhengActivity.this, ChoiceHomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(YanzhengActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    Toast.makeText(YanzhengActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    break;
                case 3:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanzheng);
        gIntent();
        initView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yanzhengCode.getText().length() != 0) {
                    HashMap map = new HashMap();
                    map.put("tel", phone);
                    map.put("passWord", pw);
                    map.put("verifi", yanzhengCode.getText().toString());
                    MyHttpUtils.okHttpUtils(map, handler, 1, 0, MyURL.Login);
                }
            }
        });

    }

    private void initView() {
        yanzhengCode = findViewById(R.id.yanzheng_code);
        button = findViewById(R.id.yanzheng_button);
        ids = new ArrayList<>();
        companyIds = new ArrayList<>();
        homeList = new ArrayList<>();
        towerList = new ArrayList<>();
        elevatorIds = new ArrayList<>();
        elevatorNumbers = new ArrayList<>();
        wifiList = new ArrayList<>();
        MyHttpUtils.okHttpGet(handler, 2, 0, MyURL.getMail + "?tel=" + phone);

    }

    private void gIntent() {
        phone = getIntent().getStringExtra("phone");
        pw = getIntent().getStringExtra("pw");
    }

    public List<User> parseUserList(String json) {
        return JSONObject.parseArray(json, User.class);
    }

    public void saveData() {
        User user = null;
        for (int i = 0; i < userList.size(); i++) {
            user = userList.get(i);
            ids.add(user.getId() + "");
            companyIds.add(user.getCompanyId() + "");
            homeList.add(user.getCompanyName());
            towerList.add(user.getTowerName());
            //elevatorIds.add(user.getElevatoId() + "");
            elevatorNumbers.add(user.getElevatorNumber());
            wifiList.add(user.getWifi());

        }
        Log.d("gaotuo", "id集合:" + ids);
        Log.d("gaotuo", "companyId集合:" + companyIds);
        Log.d("gaotuo", "homeList集合:" + homeList);
        Log.d("gaotuo", "number集合:" + elevatorNumbers);
        Log.d("gaotuo", "wifi集合:" + wifiList);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "companyId", companyIds);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "ids", ids);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "home", homeList);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "towerName", towerList);
        //SaveOrDeletePrefrence.saveList(getApplicationContext(), "elevatorId", elevatorIds);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "elevatorNumber", elevatorNumbers);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "wifi", wifiList);
    }
}
