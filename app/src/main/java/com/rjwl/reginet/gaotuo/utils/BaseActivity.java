package com.rjwl.reginet.gaotuo.utils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rjwl.reginet.gaotuo.utils.ActivityCollector;

/**
 * Created by Administrator on 2017/7/8.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this);
        Log.e(getClass().getName(),"onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
