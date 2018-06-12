package com.rjwl.reginet.gaotuo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.gaotuo.entity.AllCard;
import com.rjwl.reginet.gaotuo.entity.Kabao;
import com.rjwl.reginet.gaotuo.entity.Notice;
import com.rjwl.reginet.gaotuo.entity.User;
import com.rjwl.reginet.gaotuo.entity.Xiaoqu;
import com.rjwl.reginet.gaotuo.utils.BaseActivity;
import com.rjwl.reginet.gaotuo.utils.MyHttpUtils;
import com.rjwl.reginet.gaotuo.MyURL;
import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.utils.SaveOrDeletePrefrence;
import com.rjwl.reginet.gaotuo.utils.WifiAdmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText num;
    private EditText pw;
    private Button login;
    private TextView regist;
    private String phoneNumber;
    private String phoneCode;
    private String passWord;

    private String name = "";//用户名

    private EditText yanzhengCode;
    private TextView getCode;

    String text = "[[],[" +
            "{\"companyId\":2," +
            "\"content\":\"12512412\"," +
            "\"createTime\":1528128000000," +
            "\"id\":5,\n" +
            "\"path\":\"http://elevator.gotuo.cn/elevator/6.jpg\",\"title\":\"12415\"}," +
            "{\"companyId\":2," +
            "\"content\":\"789789\"," +
            "\"createTime\":1528128000000," +
            "\"id\":6," +
            "\"path\":\"http://elevator.gotuo.cn/elevator/8.jpg\",\"title\":\"789789\"}," +
            "{\"companyId\":2," +
            "\"content\":\"王企鹅去\"," +
            "\"createTime\":1528128000000," +
            "\"id\":7,\"path\":\"http://elevator.gotuo.cn/elevator/9.jpg\"," +
            "\"title\":\"是个大使馆\"}," +
            "{\"companyId\":2," +
            "\"content\":\"yktkgkygf\"," +
            "\"createTime\":1528128000000," +
            "\"id\":8," +
            "\"path\":\"http://elevator.gotuo.cn/elevator/10.jpg\"," +
            "\"title\":\"fgjytjt\"}," +
            "{\"companyId\":2," +
            "\"content\":\"dfnhdrhrdhbdr\"," +
            "\"createTime\":1528128000000," +
            "\"id\":9," +
            "\"path\":\"http://elevator.gotuo.cn/elevator/12mg.jpg\"," +
            "\"title\":\"fdhfjgfn\"},\n" +
            "{\"companyId\":2," +
            "\"content\":\"因为咱们小区欠了水费，一共是300元钱，物业因为300元钱出不起，所以跑路了。特此声明本小区以后可能就没水了。\"," +
            "\"createTime\":1528128000000," +
            "\"id\":10," +
            "\"path\":\"http://elevator.gotuo.cn/elevator/12mg.jpg\"," +
            "\"title\":\"停水\"}," +
            "{\"companyId\":2," +
            "\"content\":\"1564616\"," +
            "\"createTime\":1528214400000," +
            "\"id\":11," +
            "\"path\":\"http://elevator.gotuo.cn/elevator/36b3_1c5844ec_a056_d634_97a6_259ce168854d_1.jpg\"," +
            "\"title\":\"1641616\"}," +
            "{\"companyId\":2," +
            "\"content\":\"wqrfwq\"," +
            "\"createTime\":1528214400000," +
            "\"id\":12," +
            "\"path\":\"http://elevator.gotuo.cn/elevator/12mg.jpg\"," +
            "\"title\":\"asfbfxcv\"}" +
            "\t]" +
            "]";


    private List<Xiaoqu> xiaoquList;//小区
    private List<AllCard> allCardList;//所有小区
    private List<String> homeList;
    private List<String> cardList;//卡包

    private List<String> towerList;
    private List<String> elevatorNumbers;
    private List<String> wifiList;
    private List<String> powerNameList;

    /*private List<List<Notice>> noticeList;
    private List<String> companyIds;
    private List<String> contents;
    private List<String> paths;
    private List<String> titles;*/

    private Boolean threadTag = false;
    private Thread thread;
    private int count = 60;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hidenLoading();
            switch (msg.what) {
                case 0:
                    String errMsg = (String) msg.obj;
                    Toast.makeText(LoginActivity.this, "连接异常", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    String json = (String) msg.obj;
                    Log.e("验证结果", "登录返回数据:" + json);

                    try {
                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(json);
                        int code = jsonObject.getInteger("code");
                        String message = jsonObject.getString("data");
                        if ("[]".equals(message) || "".equals(message) || message == null) {
                            Toast.makeText(LoginActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (code == 200) {
                            //userList = parseUserList(message);
                            //String token = userList.get(0).getState();
                            //int id = userList.get(0).getId();
                            //name = userList.get(0).getName();
                            //xiaoquList = parseUserList(message);
                            allCardList = parseXiaoquList(message);

                            //SaveOrDeletePrefrence.saveInt(getApplicationContext(), "id", id);
                            //SaveOrDeletePrefrence.save(getApplicationContext(), "name", name);
                            SaveOrDeletePrefrence.save(getApplicationContext(), "phone", phoneNumber);
                            SaveOrDeletePrefrence.save(getApplicationContext(), "password", pw.getText() + "");

                            SaveOrDeletePrefrence.saveBoolean(getApplicationContext(), phoneNumber, true);

                            saveData();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                        }

                    } catch (com.alibaba.fastjson.JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    //更新时间
                    getCode.setText(count + "s");
                    break;
                case 4:
                    getCode.setText("获取验证码");
                    count = 60;
                    threadTag = false;
                    break;
                /*case 5:
                    String news = (String) msg.obj;
                    Log.e("验证结果", "登录返回数据:" + news);

                    try {
                        JSONObject jsonObject = JSONObject.parseObject(news);
                        int code = jsonObject.getInteger("code");
                        String message = jsonObject.getString("data");
                        if ("[]".equals(message) || "".equals(message) || message == null) {
                            Toast.makeText(LoginActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (code == 200) {
                            Log.d("TAG1", "收到的公告:" + message);
                            noticeList = parseNotice(message);
                            saveNotice();
                        } else {
                            Toast.makeText(LoginActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                        }

                    } catch (com.alibaba.fastjson.JSONException e) {
                        e.printStackTrace();
                    }
                    break;*/
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SaveOrDeletePrefrence.look(getApplicationContext(), "token").equals("")) {
            finish();
        }
    }


    private void initView() {
        num = findViewById(R.id.editText);
        pw = findViewById(R.id.editText2);
        login = findViewById(R.id.btn_login);
        //regist = findViewById(R.id.tv_register);

        yanzhengCode = findViewById(R.id.et_login_phone);
        getCode = findViewById(R.id.tv_login_get_code);

        homeList = new ArrayList<>();

        cardList = new ArrayList<>();
        towerList = new ArrayList<>();
        elevatorNumbers = new ArrayList<>();
        wifiList = new ArrayList<>();
        powerNameList = new ArrayList<>();

        /*noticeList = new ArrayList<>();
        companyIds = new ArrayList<>();
        contents = new ArrayList<>();
        paths = new ArrayList<>();
        titles = new ArrayList<>();*/

        getCode.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    /*public List<List<Notice>> parseNotice(String notic) {
        JSONArray jsonArray = JSONArray.parseArray(notic);
        List<Notice> list;
        List<List<Notice>> bigList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Log.d("TAG1", "公告数组：" + jsonArray.get(i));
            String arr = jsonArray.get(i) + "";
            if (arr == null || "".equals(arr) || "[]".equals(arr)) {
                continue;
            }
            list = JSONObject.parseArray(jsonArray.get(i) + "", Notice.class);
            bigList.add(list);
        }
        Log.d("TAG1", "所有公告集合:" + bigList);
        return bigList;
    }*/

    /*public void saveNotice() {

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
    }*/


    //解析数据
    public List<AllCard> parseXiaoquList(String json) {
       /* JSONObject jsonObject = JSONObject.parseObject(json);
        String str = jsonObject.getString("data");*/
        JSONArray jsonArray = JSONObject.parseArray(json);
        AllCard allCard = null;
        List<AllCard> allCards = new ArrayList<>();
        //Log.d("TAG1", str);
        for (int i = 0; i < jsonArray.size(); i++) {
            Log.d("TAG1", "解析数组: " + jsonArray.get(i));
            xiaoquList = JSONObject.parseArray(jsonArray.get(i) + "", Xiaoqu.class);
            Log.d("TAG1", "解析后的小区对象:" + xiaoquList);
            allCard = new AllCard(xiaoquList);
            allCards.add(allCard);
        }
        return allCards;
    }

    public void saveData() {
        Xiaoqu xiaoqu = null;
        String saveName = "";
        for (int i = 0; i < allCardList.size(); i++) {
            List<Xiaoqu> list = allCardList.get(i).getXiaoquList();
            Log.d("TAG1", "设备集合:" + list);
            for (int j = 0; j < list.size(); j++) {
                xiaoqu = list.get(j);
                if (xiaoqu == null || xiaoqu.getTower() == null) {
                    continue;

                }
                Log.d("TAG1", "设备:" + xiaoqu);
                saveName = xiaoqu.getTower().getTowerName();
                cardList.add(xiaoqu.getCard());//卡包
                towerList.add(xiaoqu.getTower().getTowerName());//设备名
                elevatorNumbers.add(xiaoqu.getTower().getElevatorNumber());//设备号
                wifiList.add(xiaoqu.getTower().getWifi());
                if (j == 0) {
                    homeList.add(xiaoqu.getTower().getCompanyName());//小区名
                }
                List<Kabao> kabaos = xiaoqu.getTowerPowerList();
                for (int k = 0; k < kabaos.size(); k++) {
                    powerNameList.add(kabaos.get(k).getPowerName());
                }
                SaveOrDeletePrefrence.saveList(getApplicationContext(), saveName, powerNameList);
                Log.d("gaotuo", "powerNameList:" + powerNameList);
                powerNameList.clear();
            }
            SaveOrDeletePrefrence.saveList(getApplicationContext(), homeList.get(i) + "card", cardList);
            SaveOrDeletePrefrence.saveList(getApplicationContext(), homeList.get(i) + "tower", towerList);//设备名
            SaveOrDeletePrefrence.saveList(getApplicationContext(), homeList.get(i) + "elevatorNumber", elevatorNumbers);
            SaveOrDeletePrefrence.saveList(getApplicationContext(), homeList.get(i) + "wifi", wifiList);
            Log.d("gaotuo", "卡包集合:" + cardList);
            Log.d("gaotuo", "towerList:" + towerList);
            Log.d("gaotuo", "number集合:" + elevatorNumbers);
            Log.d("gaotuo", "wifi集合:" + wifiList);
            cardList.clear();
            towerList.clear();
            elevatorNumbers.clear();
            wifiList.clear();
        }
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "homeList", homeList);
        Log.d("gaotuo", "homeList集合:" + homeList);


       /* User user = null;
        for (int i = 0; i < userList.size(); i++) {
            user = userList.get(i);
            ids.add(user.getId() + "");
            companyIds.add(user.getCompanyId() + "");
            homeList.add(user.getCompanyName());//小区名
            towerList.add(user.getTowerName());//设备名
            //elevatorIds.add(user.getElevatoId() + "");
            //homeTowerList.add(user.getCompanyName() + ":" + user.getTowerName());

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
        //SaveOrDeletePrefrence.saveList(getApplicationContext(), "homeAndTower", homeTowerList);
        //SaveOrDeletePrefrence.saveList(getApplicationContext(), "elevatorId", elevatorIds);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "elevatorNumber", elevatorNumbers);
        SaveOrDeletePrefrence.saveList(getApplicationContext(), "wifi", wifiList);*/
    }

    @Override
    public void onClick(View v) {
        phoneNumber = num.getText() + "";
        passWord = pw.getText() + "";

        switch (v.getId()) {
            case R.id.tv_login_get_code:
                Log.d("TAG1", "获取验证码");
                if ("".equals(phoneNumber) || phoneNumber.length() != 11) {
                    Toast.makeText(LoginActivity.this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (threadTag == false) {
                    upDataTime();
                    MyHttpUtils.okHttpGet(handler, 2, 0, MyURL.getMail + "?tel=" + phoneNumber);
                }

                break;
            case R.id.btn_login:

                //测试
               /* noticeList = parseNotice(text);
                saveNotice();*/

                phoneCode = yanzhengCode.getText() + "";
                if ("".equals(phoneNumber) || phoneNumber.length() != 11) {
                    Toast.makeText(LoginActivity.this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(passWord) || passWord == null) {
                    Toast.makeText(LoginActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(phoneCode) || phoneCode == null) {
                    Toast.makeText(LoginActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoading();
                HashMap<String, String> map = new HashMap();
                map.put("tel", phoneNumber);
                map.put("passWord", passWord);
                map.put("verifi", phoneCode);
                //MyHttpUtils.okHttpGet(handler, 5, 0, MyURL.getNews + "?tel=" + phoneNumber);
                MyHttpUtils.okHttpUtils(map, handler, 1, 0, MyURL.Login);
                break;
        }
    }

    private void upDataTime() {
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                threadTag = true;
                while (threadTag) {
                    count--;
                    if (count > 0) {
                        handler.sendEmptyMessage(3);
                    } else if (count == 0) {
                        handler.sendEmptyMessage(4);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        thread.start();
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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
