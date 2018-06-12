package com.rjwl.reginet.gaotuo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.rjwl.reginet.gaotuo.entity.Kabao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DB_NAME = "gaotuo.db";
    private final static String TABLE_NAME = "mydatas";
    private final static String CREATE_TBL = "create table mydatas(wifiName text, wifiPassword text, code text)";

    private SQLiteDatabase db;

    //SQLiteOpenHelper子类必须要的一个构造函数
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //必须通过super 调用父类的构造函数
        super(context, name, factory, version);
    }

    //数据库的构造函数，传递三个参数的
    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    //数据库的构造函数，传递一个参数的， 数据库名字和版本号都写死了
    public DBHelper(Context context) {
        this(context, DB_NAME, null, VERSION);
    }

    // 回调函数，第一次创建时才会调用此函数，创建一个数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        System.out.println("Create Database");
        db.execSQL(CREATE_TBL);
    }

    //回调函数，当你构造DBHelper的传递的Version与之前的Version调用此函数
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update Database");

    }

    //插入数据
    public void insert(Kabao kabao) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("wifiName", kabao.getWifiName());
        values.put("wifiPassword", kabao.getWifiPassword());
        values.put("code", kabao.getCode());
        db.insert(TABLE_NAME, null, values);
        Log.e("插入成功", "Name" + kabao.getWifiName());
        db.close();
    }

    // 遍历数据库
    public List<Kabao> query() {
        db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        List<Kabao> list = new ArrayList<>();
        while (c.moveToNext()) {
            Kabao bean = new Kabao();
            bean.setWifiName(c.getString(0));
            bean.setWifiPassword(c.getString(1));
            bean.setCode(c.getString(2));
            list.add(bean);
        }
        db.close();
        return list;
    }

//    // 按id查询数据库
//    public Kabao rawQuery(int id) {
//        db = getWritableDatabase();
//        Cursor c = db.query(TABLE_NAME, null, "id=?", new String[]{id + ""}, null, null, null);
//        LianxuEntity.DataBean bean = null;
//        while (c.moveToNext()) {
//            bean = new LianxuEntity.DataBean();
//            bean.setSid(Integer.parseInt(c.getString(0)));
//            bean.setAudio_time(Integer.parseInt(c.getString(1)));
//            bean.setAudio_size(Integer.parseInt(c.getString(2)));
//            bean.setTitle(c.getString(3));
//            bean.setAudio_url(c.getString(4));
//            bean.setPath(c.getString(5));
//            bean.setImg_url(c.getString(6));
//            bean.setFalse_zan(Integer.parseInt(c.getString(7)));
//            bean.setText(c.getString(8));
//            bean.setAuthor_img_url(c.getString(9));
//            bean.setAuthor(c.getString(10));
//            bean.setUpdate_time(c.getString(11));
//            bean.setPerson_image_url(c.getString(12));
//            bean.setCid(c.getInt(13));
//        }
//        db.close();
//        return bean;
//    }

    public Kabao rawQueryUrl(String url) {
        db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, null, "url=?", new String[]{url + ""}, null, null, null);
        Kabao bean = null;
        while (c.moveToNext()) {
            bean = new Kabao();
            bean.setWifiName(c.getString(0));
            bean.setWifiPassword(c.getString(1));
            bean.setCode(c.getString(2));
        }
        db.close();
        return bean;
    }

//    //按id删除数据
//    public void del(int id) {
//        db = getWritableDatabase();
//        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
//        db.close();
//    }

    //关闭数据库
    public void close() {
        if (db != null)
            db.close();
    }

    public void deleteAll() {
        db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
