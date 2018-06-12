package com.rjwl.reginet.gaotuo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.utils.BaseActivity;


/**
 * Created by Administrator on 2018/4/23.
 *
 */

public class MessageActivity extends BaseActivity {
    private ImageView backMessage;
    private TextView titleMessage;
    private TextView tv_message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        backMessage = findViewById(R.id.back_message);
        titleMessage = findViewById(R.id.title_message);
        tv_message = findViewById(R.id.tv_message);

        titleMessage.setText(title);
        backMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
}
