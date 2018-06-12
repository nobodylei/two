package com.rjwl.reginet.gaotuo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.rjwl.reginet.gaotuo.entity.User;

/**
 * Created by Administrator on 2018/1/27.
 */

public class MyApp extends Application {

    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
