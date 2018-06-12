package com.rjwl.reginet.gaotuo.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/3.
 */

public class MyHttpUtils {
    public static void okHttpUtils(HashMap map, final Handler handler, final int rightCode, final int erroCode, String url) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(8, TimeUnit.SECONDS).build();//设置读取超时时间;
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            builder.add(entry.getKey() + "", entry.getValue() + "");
            //Log.e(entry.getKey() + "", entry.getValue() + "");
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                //Log.d("TAG1", "MyHttpUtils " + call.toString());
                if(e instanceof SocketTimeoutException){//判断超时异常
                    message.obj = "连接超时";
                }
                /*if(e instanceof ConnectException){//判断连接异常，我这里是报Failed to connect to 10.7.5.144
                    message.obj = "连接异常";
                }*/
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                Log.d("TAG1", "接收的返回:" + message.obj.toString());
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });

    }

    public static void okHttpUtilsHead(Context context, HashMap map, final Handler handler, final int rightCode, final int erroCode, String url) {

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            builder.add(entry.getKey() + "", entry.getValue() + "");
            Log.e(entry.getKey() + "", entry.getValue() + "");
        }
        Log.e("token", SaveOrDeletePrefrence.look(context, "token") + "");
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token", SaveOrDeletePrefrence.look(context, "token"))
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });

    }

    public static void okHttpGet(final Handler handler, final int rightCode, final int erroCode, String url) {
        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(8, TimeUnit.SECONDS).build();//设置读取超时时间;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.e("get", "url:" + url);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                if(e instanceof SocketTimeoutException){//判断超时异常
                    message.obj = "连接超时";
                }
                if(e instanceof ConnectException){//判断连接异常，我这里是报Failed to connect to 10.7.5.144
                    message.obj = "连接异常";
                }
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });
    }


    public static void header(Context context, final Handler handler, final int rightCode, final int erroCode, String url) {
        OkHttpClient mHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token", SaveOrDeletePrefrence.look(context, "token"))
                .build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });

    }
}
