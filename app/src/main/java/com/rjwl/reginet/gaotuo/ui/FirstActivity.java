package com.rjwl.reginet.gaotuo.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.rjwl.reginet.gaotuo.MyURL;
import com.rjwl.reginet.gaotuo.entity.Notice;
import com.rjwl.reginet.gaotuo.utils.MyHttpUtils;
import com.rjwl.reginet.gaotuo.utils.SaveOrDeletePrefrence;
import com.rjwl.reginet.gaotuo.utils.WifiAdmin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstActivity extends Activity {

    private WifiAdmin wifiAdmin;
    private String phone;
    private String password;
    private String token;
    private Map<String, String> map;

    private List<List<Notice>> noticeList;
    private List<String> companyIds;
    private List<String> contents;
    private List<String> paths;
    private List<String> titles;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    /*SharedPreferences sharedPreferences = getApplication().getSharedPreferences("wel",
                            Activity.MODE_PRIVATE);*/
                    if ("".equals(phone) || "".equals(password)) {
                        Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    break;
                case 1:
                    String josn = (String) msg.obj;
                    Log.e("验证结果", ":" + josn);

                    try {
                        JSONObject jsonObject = new JSONObject(josn);
                        int code = jsonObject.getInt("code");
                        String message = jsonObject.getString("message");
                        if (code == 1) {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                            token = jsonObject1.getString("token");

                            SaveOrDeletePrefrence.save(getApplicationContext(), "token", token);
                             //SaveOrDeletePrefrence.save(getApplicationContext(), phone, "true");
                            SaveOrDeletePrefrence.saveBoolean(getApplicationContext(), phone, true);
                            Intent intent = new Intent(FirstActivity.this, ChoiceHomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(FirstActivity.this, YanzhengActivity.class);
                            intent.putExtra("phone", phone);
                            intent.putExtra("pw", password);
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(FirstActivity.this, "提示:" + message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case 5:
                    String news = (String) msg.obj;
                    Log.e("验证结果", "登录返回数据:" + news);

                    try {
                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(news);
                        int code = jsonObject.getInteger("code");
                        String message = jsonObject.getString("data");
                        if ("[]".equals(message) || "".equals(message) || message == null) {
                            Toast.makeText(FirstActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (code == 200) {
                            Log.d("TAG1", "收到的公告:" + message);
                            noticeList = parseNotice(message);
                            saveNotice();
                        } else {
                            Toast.makeText(FirstActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                        }

                    } catch (com.alibaba.fastjson.JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public List<List<Notice>> parseNotice(String notic) {
        JSONArray jsonArray = JSONArray.parseArray(notic);
        List<Notice> list;
        List<List<Notice>> bigList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Log.d("TAG1", "公告数组：" + jsonArray.get(i));
            String arr = jsonArray.get(i) + "";
            if (arr == null || "".equals(arr) || "[]".equals(arr)) {
                continue;
            }
            list = com.alibaba.fastjson.JSONObject.parseArray(jsonArray.get(i) + "", Notice.class);
            bigList.add(list);
        }
        Log.d("TAG1", "所有公告集合:" + bigList);
        return bigList;
    }

    public void saveNotice() {

        for (int i = 0; i < noticeList.size(); i++) {
            List<Notice> list = noticeList.get(i);
            for (int j = 0; j < list.size(); j++) {
                Notice notice = list.get(j);
                if (j == 0) {
                    companyIds.add(notice.getCompanyId() + "");
                }
                contents.add(notice.getContent());
                paths.add(notice.getPath());
                titles.add(notice.getTitle());
            }
            SaveOrDeletePrefrence.saveList(getApplicationContext(), companyIds.get(i) + "content", contents);
            SaveOrDeletePrefrence.saveList(getApplicationContext(), companyIds.get(i) + "path", paths);
            SaveOrDeletePrefrence.saveList(getApplicationContext(), companyIds.get(i) + "title", titles);
            Log.d("gaotuo", "内容集合:" + contents);
            Log.d("gaotuo", "图片:" + paths);
            Log.d("gaotuo", "标题:" + titles);

            contents.clear();
            paths.clear();
            titles.clear();
        }
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "companyId", companyIds);
        Log.d("gaotuo", "companyIds:" + companyIds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wifiAdmin = new WifiAdmin(getApplicationContext());
        wifiAdmin.openWifi();

        noticeList = new ArrayList<>();
        companyIds = new ArrayList<>();
        contents = new ArrayList<>();
        paths = new ArrayList<>();
        titles = new ArrayList<>();

        phone = SaveOrDeletePrefrence.look(getApplicationContext(), "phone");
        password = SaveOrDeletePrefrence.look(getApplicationContext(), "password");
        token = SaveOrDeletePrefrence.look(getApplicationContext(), "token");

        if (!"".equals(phone)) {
            MyHttpUtils.okHttpGet(handler, 5, 0, MyURL.getNews + "?tel=" + phone);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);

                    /*if ("".equals(phone) || "".equals(password)) {

                        handler.sendEmptyMessage(0);
                    } else {

                        map = new HashMap<>();
                        map.put("phone", phone);
                        map.put("password", password);
                        map.put("token", token);
                        MyHttpUtils.okHttpUtils((HashMap) map, handler, 1, 0, null);
                    }*/

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
