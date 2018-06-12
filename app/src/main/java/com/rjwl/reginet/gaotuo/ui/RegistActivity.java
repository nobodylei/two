package com.rjwl.reginet.gaotuo.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rjwl.reginet.gaotuo.MyURL;
import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.utils.SaveOrDeletePrefrence;
import com.rjwl.reginet.gaotuo.utils.MyHttpUtils;
import com.rjwl.reginet.gaotuo.utils.WifiAdmin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegistActivity extends AppCompatActivity {
    private TextView registBack;
    private TextView registGetCode;
    private EditText registPhone;
    private EditText registCode;
    private EditText registPw;
    private EditText registPw2;
    private EditText registName;
    private Button button2;


    private Boolean threadTag = false;
    private Thread thread;
    private int count = 60;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String errMsg = (String) msg.obj;
                    Toast.makeText(RegistActivity.this, errMsg, Toast.LENGTH_SHORT).show();
                    break;
                case 1:

                    registGetCode.setText(count + "秒后重新获取");
                    break;
                case 2:
                    registGetCode.setText("获取验证码");
                    count = 60;
                    threadTag = false;
                    break;
                case 3:
                    String json = (String) msg.obj;
                    Log.e("tag", ":" + json);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int code = jsonObject.getInt("code");
                        String message = jsonObject.getString("message");
                        if (code == 1) {
                            upDataTime();
                        }
                        Toast.makeText(RegistActivity.this, "提示:" + message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 4:
                    String json2 = (String) msg.obj;
                    Log.e("tag", ":" + json2);
                    try {
                        JSONObject jsonObject = new JSONObject(json2);
                        int code = jsonObject.getInt("code");
                        String message = jsonObject.getString("message");
                        Toast.makeText(RegistActivity.this, "提示:" + message, Toast.LENGTH_LONG).show();
                        if (code == 1) {
                            //注册
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                            String token = jsonObject1.getString("token");
                            SaveOrDeletePrefrence.save(RegistActivity.this, "token", token);
                            SaveOrDeletePrefrence.save(RegistActivity.this, registPhone.getText().toString(), "true");
                            Log.e("tag", ":" + token);

                            Intent intent = new Intent(RegistActivity.this, ChoiceHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    Toast.makeText(RegistActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();

        setOnclick();


    }

    private void setOnclick() {
        registBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        registGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (threadTag == false) {
                    if (registPhone.getText().length() != 0) {
                        HashMap map = new HashMap();
                        map.put("phone", registPhone.getText());
                        MyHttpUtils.okHttpUtils(map, handler, 3, 0, MyURL.GetCode);
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入手机号！", Toast.LENGTH_LONG).show();
                    }
                }
                if(!WifiAdmin.isNetworkConnected(RegistActivity.this)) {
                    handler.sendEmptyMessage(5);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (registPhone.getText().length() != 0) {
                    if (registCode.getText().length() != 0) {
                        if (registPw.getText().length() != 0 && registPw2.getText().length() != 0 && registName.getText().length() != 0) {
                            if (!registPw.getText().toString().equals(registPw2.getText().toString())) {
                                Toast.makeText(RegistActivity.this, "两次填写密码不一致！", Toast.LENGTH_LONG).show();
                            } else {
                                HashMap map = new HashMap();
                                map.put("phone", registPhone.getText().toString());
                                map.put("code", registCode.getText().toString());
                                map.put("name", registName.getText().toString());
                                map.put("password", registPw.getText().toString());
                                MyHttpUtils.okHttpUtils(map, handler, 4, 0, MyURL.Regist);
                            }

                        } else {
                            Toast.makeText(RegistActivity.this, "请填写完整信息！", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RegistActivity.this, "请填写验证码！", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RegistActivity.this, "请填写手机号！", Toast.LENGTH_LONG).show();
                }

                if(!WifiAdmin.isNetworkConnected(RegistActivity.this)) {
                    handler.sendEmptyMessage(5);
                }


            }
        });
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
                        handler.sendEmptyMessage(1);
                    } else if (count == 0) {
                        handler.sendEmptyMessage(2);
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


    private void initView() {
        registBack = findViewById(R.id.regist_back);
        registGetCode = findViewById(R.id.regist_get_code);
        registPhone = findViewById(R.id.regist_phone);
        registCode = findViewById(R.id.regist_code);
        registPw = findViewById(R.id.regist_pw);
        registPw2 = findViewById(R.id.regist_pw2);
        registName = findViewById(R.id.regist_name);
        button2 = findViewById(R.id.button2);
    }
}
