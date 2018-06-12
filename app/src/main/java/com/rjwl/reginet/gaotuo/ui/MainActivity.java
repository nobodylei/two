package com.rjwl.reginet.gaotuo.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.gaotuo.adapter.KabaoAdapter;
import com.rjwl.reginet.gaotuo.adapter.ViewPagerAdapter;
import com.rjwl.reginet.gaotuo.entity.AllCard;
import com.rjwl.reginet.gaotuo.entity.User;
import com.rjwl.reginet.gaotuo.utils.ActivityCollector;
import com.rjwl.reginet.gaotuo.utils.BaseActivity;
import com.rjwl.reginet.gaotuo.utils.SaveOrDeletePrefrence;
import com.rjwl.reginet.gaotuo.utils.StrUtil;
import com.rjwl.reginet.gaotuo.utils.WifiAdmin;
import com.rjwl.reginet.gaotuo.view.FullyLinearLayoutManager;
import com.rjwl.reginet.gaotuo.entity.Kabao;
import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.adapter.WifiAdapter;
import com.rjwl.reginet.gaotuo.entity.WifiCard;
import com.rjwl.reginet.gaotuo.entity.WifiEntity;
import com.rjwl.reginet.gaotuo.entity.Xiaoqu;
import com.rjwl.reginet.gaotuo.adapter.XiaoquAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    byte readBuf[] = new byte[50];
    byte random[] = new byte[4];

    int WIFISTATE;

    private ListView floorListView;
    private List<String> floorList;
    private ArrayAdapter floorAdapter;
    private int floorCount;

    private DrawerLayout drawerlayout;
    private ImageView gengduo;
    private ImageView shuaxin;
    private TextView genggaiAnniu;
    private TextView tvLouhao;

    private ViewPager viewPager;
    private LinearLayout linearLayout;

    String[] imageUris = {
            "http://img02.tooopen.com/images/20150828/tooopen_sy_140423486639.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3145230522,2840243223&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=785293575,980626410&fm=27&gp=0.jpg",
    };
    SimpleDraweeView[] simpleDraweeViews;
    private ImageView[] points;// 圆点集合
    private int currentItem;
    private ScheduledExecutorService executor;
    private ViewPagerAdapter viewPagerAdapter;

    private boolean wifiState = false;

    private MediaPlayer mediaPlayerOk;
    private MediaPlayer mediaPlayerFail;
    private boolean isVoice = false;

    private NavigationView navView;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private boolean exit_tag;

    private WifiAdmin wa;
    private List<ScanResult> mWifiList;
    private WifiManager wm;

    private List<WifiEntity> wifiList;

    private WifiAdapter wifiAdapter;

    private ListView wifiListView;
    private RelativeLayout emptyView;

    private List<Xiaoqu> list;
    private RecyclerView recycleView;
    private XiaoquAdapter xiaoquAdapter;
    private String sPID = "";
    private String wifiSsid = "";
    private String wifiPassword = "";

    private String floor = "";
    private List<Integer> power;

    private IntentFilter filter;
    private WifiReceiver wifiReceiver;

    private Thread thread;

    MyThread myThread;
    private WifiCard user;
    private boolean ctrlBusy = false;
    byte[] bTime = {0, 0, 0, 0, 0, 0, 0};

    private View nav_view;
    private TextView tv_name;
    private TextView tv_phone;
    private String phoneNumber = "";
    private String name = "";

    /*private String tower = "";
    private String home = "";*/

    //private byte[] uId = {(byte)0x01, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88};

    private LocationManager lm;
    private boolean isGps;

    private String share = "";

    //修改后
    //设备名加小区名
    //private List<Xiaoqu> xiaoquList;//小区
    //private List<AllCard> allCardList;//所有小区
    private List<String> homeList;
    private List<String> cardList;//卡包

    private List<String> towerList;
    private List<String> elevatorNumbers;
    private List<String> wifiPwList;
    private List<String> powerNameList;

    private List<String> allCards;
    private List<String> alltowers;
    private List<String> allwifis;
    private List<String> allNumbers;
    private List<List<String>> allPowers;

    private List<String> showWifi;
    private List<String> showWifiPw;
    private List<String> showCard;
    private List<List<String>> showPower;

    private List<String> currPower;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (wifiState) {
                        floorDialog();
                        wifiState = false;
                    }
                    break;
                case 1:
                    if (wifiList.size() == 0) {
                        wifiListView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        //Toast.makeText(MainActivity.this, "请检查是否打开GPS", Toast.LENGTH_SHORT).show();
                    } else {
                        wifiListView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }
                    wifiAdapter.notifyDataSetChanged();
                    break;
                case 0x11:
                    Toast.makeText(MainActivity.this, "开始", Toast.LENGTH_SHORT).show();
                    break;
                case 0x22:
                    Toast.makeText(MainActivity.this, "获取随机数", Toast.LENGTH_SHORT).show();
                    break;
                case 0x33:
                    Bundle bundle1 = msg.getData();
                    // ctrlstatus = DEV_CONTROL;
                    Toast.makeText(MainActivity.this, "完成，发送控制" + bundle1.getString("msg"), Toast.LENGTH_SHORT).show();
                    break;
                case 0x44:
                    if (isVoice)
                        mediaPlayerFail.start();
                    Bundle bundle2 = msg.getData();
                    // ctrlstatus = DEV_CONTOK;
                    Toast.makeText(MainActivity.this, bundle2.getString("msg"), Toast.LENGTH_SHORT).show();

                    break;
                case 0x55:
                    Toast.makeText(MainActivity.this, "关闭", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filter = new IntentFilter();
        wifiReceiver = new WifiReceiver();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiReceiver, filter);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);//位置

        user = new WifiCard(getApplicationContext());

        initView();
        SetOnClick();
        initRecycleView();
        initAudio();
        //initPower();
        wa = new WifiAdmin(getApplicationContext());
        wa.openWifi();
        /*Log.d("TAG1", "wifi的SSID" + wifiSsid);
        Log.d("TAG1", "wifi的密码" + wifiPassword);*/

        //连接选择的网络
        //wa.addNetwork(wa.CreateWifiInfo(MainActivity.this, wifiSsid, wifiPassword, 2));
    }


    private void initPower() {
        //wifiPassword = SaveOrDeletePrefrence.look(getApplicationContext(), "wifiPassword");
        //"22222222123456784321FF000000000000001205010101010112300000E70B";//
        //sPID = SaveOrDeletePrefrence.look(getApplicationContext(), "card");
        //wifiSsid = SaveOrDeletePrefrence.look(getApplicationContext(), "wifiSsid");//sPID.substring(8, 20);


        floor = sPID.substring(20, 36);
        power = StrUtil.getPower(floor);
        Collections.sort(power);
        floorCount = power.size();
    }


    private void initAudio() {

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayerOk = new MediaPlayer();
        mediaPlayerFail = new MediaPlayer();
        mediaPlayerOk.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayerFail.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayerOk.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                player.seekTo(0);
            }
        });
        mediaPlayerFail.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                player.seekTo(0);
            }
        });
        AssetFileDescriptor fileOk = getResources().openRawResourceFd(R.raw.ok);
        AssetFileDescriptor fileFail = getResources().openRawResourceFd(R.raw.fail);

        try {
            mediaPlayerOk.setDataSource(fileOk.getFileDescriptor(), fileOk.getStartOffset(), fileOk.getLength());
            mediaPlayerFail.setDataSource(fileFail.getFileDescriptor(), fileFail.getStartOffset(), fileFail.getLength());
            fileOk.close();
            fileFail.close();
            mediaPlayerOk.setVolume(0.5f, 0.5f);
            mediaPlayerFail.setVolume(0.5f, 0.5f);
            mediaPlayerOk.prepare();
            mediaPlayerFail.prepare();

        } catch (IOException ioe) {
            Log.w("TAG", ioe);
            mediaPlayerOk = null;
            mediaPlayerFail = null;
        }
    }

    private KabaoAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new KabaoAdapter.OnRecyclerviewItemClickListener() {
        TextView tvKabao;

        @Override
        public void onClick(View v, int position) {
            tvKabao = v.findViewById(R.id.tv_kabao);
            String towerName = tvKabao.getText() + "";
            tvLouhao.setText(towerName);

            sPID = SaveOrDeletePrefrence.look(getApplicationContext(), towerName + "card");
            currPower.clear();
            currPower.addAll(SaveOrDeletePrefrence.getList(getApplicationContext(), towerName));
            String wifi = SaveOrDeletePrefrence.look(getApplicationContext(), towerName + "elevator");
            String wifiPw = SaveOrDeletePrefrence.look(getApplicationContext(), towerName + "wifi");

            Log.d("TAG1", "点击设备:" + sPID + ";" + currPower + ";" + wifi + ";" + wifiPw);

            wa.addNetwork(wa.CreateWifiInfo(MainActivity.this, wifi, wifiPw, 2));

            if (isWifiConnected()) {
                wifiState = true;
                handler.sendEmptyMessage(0);
            }
            Log.d("TAG1", "下标:" + position);
        }

        @Override
        public void onLongClick(View v, int position) {
            //Toast.makeText(MainActivity.this, "暂未开通", Toast.LENGTH_SHORT).show();
            tvKabao = v.findViewById(R.id.tv_kabao);
            share = tvKabao.getText().toString();
            Log.d("Long", "onLongClick" + tvKabao.getText());
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MainActivity.this.getMenuInflater().inflate(R.menu.kabao_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kabao_share:
                //Toast.makeText(this, "分享" + share, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initRecycleView() {
        list = new ArrayList<>();
        xiaoquAdapter = new XiaoquAdapter(this, list, onRecyclerviewItemClickListener);
        recycleView.setLayoutManager(new FullyLinearLayoutManager(this));
        /*TextView tvXiaoquName = recycleView.findViewById(R.id.xiaoqu_name);
        tvXiaoquName.setText(home);*/
        recycleView.setAdapter(xiaoquAdapter);
        //registerForContextMenu(recycleView);//注册上下文菜单
        //获取卡包（从数据库）
        loadXQDatas();
    }


    private void loadXQDatas() {

        for (int i = 0; i < homeList.size(); i++) {
            List<Kabao> list1 = new ArrayList<>();
            Kabao kabao1;
            towerList = SaveOrDeletePrefrence.getList(getApplicationContext(), homeList.get(i) + "tower");
            for (int j = 0; j < towerList.size(); j++) {
                kabao1 = new Kabao();
                kabao1.setWifiName(towerList.get(j));
                list1.add(kabao1);
            }
            /*kabao1.setCode(wifiSsid);
            kabao1.setWifiPassword(wifiPassword);*/
            list.add(new Xiaoqu(list1, homeList.get(i)));
        }
    }

    private void SetOnClick() {
        gengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tv_name.setText(getIntent().getStringExtra("phone"));
                drawerlayout.openDrawer(Gravity.LEFT);
            }
        });
        shuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startRun();
                Log.d("tag", "刷新");
                xiaoquAdapter.notifyDataSetChanged();
//                DBHelper dbHelper = new DBHelper(getApplicationContext());
//                Kabao kabao1 = new Kabao("reginet1","mimashi123456","");
//                Kabao kabao2 = new Kabao("reginet2","mimashi123456","");
//
//                dbHelper.insert(kabao1);
//                dbHelper.insert(kabao2);
            }
        });

        genggaiAnniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = alertDialogBuilder.create();
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.wifi_dialog, null);
                initWifiDialogView(view1);
                alertDialog.setView(view1);
                alertDialog.show();
                wa.openWifi();
                getWifi();
                settingWifiDialog(alertDialog);
            }
        });
    }

    private void floorDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alertDialog = null;
        alertDialog = alertDialogBuilder.create();
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.floor_dialog, null);
        initPower();
        initFloorDialogView(view);
        alertDialog.setView(view);
        alertDialog.show();
        settingWifiDialog(alertDialog);
    }

    private void initFloorDialogView(View view) {
        floorList = new ArrayList<>();
        //List<String> list = showPower.get(i);

        for (int i = power.size() - 1; i >= 0; i--) {
            floorList.add(currPower.get(power.get(i) - 1));
        }
        floorListView = view.findViewById(R.id.floor_recycleView);
        floorAdapter = new ArrayAdapter(this, R.layout.floor_item, floorList);
        floorListView.setAdapter(floorAdapter);

        floorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //点击楼层
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                /*Log.d("binary1", "楼层：" + floorList.get(i));
                Log.d("binary1", "命令：" + power.get(floorCount - 1 - i));*/
                user.GetCtrl(sPID);
                int cmd = 4;
                myThread = new MyThread(cmd, power.get(floorCount - 1 - i) + "");
                myThread.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        unregisterReceiver(wifiReceiver);
    }

    //设置wifi dialog 大小
    private void settingWifiDialog(AlertDialog alertDialog) {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
    }


    private void initWifiDialogView(View view1) {
        wifiList = new ArrayList<>();
        wifiListView = view1.findViewById(R.id.wifi_recycleView);
        emptyView = view1.findViewById(R.id.wifi_dialog_empty);

        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wa.openWifi();
                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(MainActivity.this, "请检查是否打开GPS", Toast.LENGTH_SHORT).show();
                }
                //getWifi();
            }
        });
        wifiAdapter = new WifiAdapter(wifiList, MainActivity.this, showWifi);
        wifiListView.setAdapter(wifiAdapter);
        wifiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //wifiList.get(i);
                //getType();
                wifiState = true;
                String ssid = wifiList.get(i).getSsid();
                String password = showWifi.get(i);
                currPower.clear();
                currPower.addAll(showPower.get(i));
                sPID = showCard.get(i);
                wa.addNetwork(wa.CreateWifiInfo(MainActivity.this, ssid, password, 2));
                //WifiI
                /*Log.d("ssid", wa.getSsid());
                Log.d("ssid", ssid);*/
//                if (isWifiConnected() && ssid.equals(wa.getSsid().replaceAll("\"", ""))) {
//                    Log.d("wifiOk", "连接WiFi，");
                handler.sendEmptyMessage(0);
                //}
                Log.d("wifiOk", "连接WiFi中....");
                TextView tvWifiItem = view.findViewById(R.id.tv_wifi_item);
                //改变楼号
                tvLouhao.setText(tvWifiItem.getText() + "");
            }
        });
    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return (networkInfo != null && networkInfo.isConnected());
    }

    //List<ScanResult> listb;

    public void getWifi() {

        thread = new Thread() {
            @Override
            public void run() {
                super.run();

                while (true) {
                    try {
                        Thread.sleep(100);
                        wa.startScan();
                        mWifiList = wa.getWifiList();
                        //Log.d("tag", listb.size() + "");
                        if (mWifiList != null) {
                            wifiList.clear();
                            for (int i = 0; i < mWifiList.size(); i++) {
                                ScanResult scanResult = mWifiList.get(i);
                                String ssid = scanResult.SSID;
                                //Log.d("TAG1", "WiFi:" + wifiSsid + " ; " + ssid);
                                if (!allNumbers.contains(ssid)) {
                                    continue;//过滤wifi
                                } else {
                                    int index = allNumbers.indexOf(ssid);
                                    showWifi.add(alltowers.get(index));
                                    showWifiPw.add(allwifis.get(index));
                                    showCard.add(allCards.get(index));
                                    showPower.add(allPowers.get(index));
                                }
                                /*if (!wifiSsid.equals(ssid)) {
                                    continue;
                                }*/
                                String bssid = scanResult.BSSID;
                                String capabilities = scanResult.capabilities;
                                //Log.d("tag", ssid + " " + bssid);
                                int frequency = scanResult.frequency;
                                int level = scanResult.level;
                                WifiEntity wifiEntity = new WifiEntity(ssid, bssid, capabilities, frequency, level);
                                wifiList.add(wifiEntity);
                            }
                        } else {

                        }
                        handler.sendEmptyMessage(1);

                    } catch (InterruptedException e) {

                    }
                }

            }
        };
        thread.start();

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPermission();
        startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    private void startAutoScroll() {
        stopAutoScroll();
        executor = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new Runnable() {
            @Override
            public void run() {
                selectNextItem();
            }

            private void selectNextItem() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(++currentItem);
                    }
                });
            }
        };
        int delay = 4;
        int period = 5;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        executor.scheduleAtFixedRate(command, delay, period, timeUnit);
    }

    private void stopAutoScroll() {
        if (executor != null) {
            executor.shutdownNow();
        }

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionList = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_WIFI_STATE);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CHANGE_WIFI_STATE);
            }

            if (!permissionList.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
            } else {
            }
        }


    }


    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
            drawerlayout.closeDrawer(Gravity.LEFT);
        } else if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        } else {

            if (exit_tag == false) {
                Toast.makeText(getApplicationContext(), "再次点击退出程序！", Toast.LENGTH_SHORT).show();

                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                exit_tag = true;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                exit_tag = false;
                            }
                        }
                ).start();
            } else {
                wa.removeWifiBySsid("\"" + wifiSsid + "\"");
                ActivityCollector.finishAll();
                System.exit(0);
            }
        }
    }

    private void initList() {
        allCards = new ArrayList<>();
        allNumbers = new ArrayList<>();
        allPowers = new ArrayList<>();
        alltowers = new ArrayList<>();
        allwifis = new ArrayList<>();
        showWifi = new ArrayList<>();
        showWifiPw = new ArrayList<>();
        showCard = new ArrayList<>();
        showPower = new ArrayList<>();
        currPower = new ArrayList<>();

        //allCardList = new ArrayList<>();
        homeList = SaveOrDeletePrefrence.getList(getApplicationContext(), "homeList");
        //Xiaoqu xiaoqu = null;
        //AllCard allCard = null;
        for (int i = 0; i < homeList.size(); i++) {
            User user1 = null;
            List<Xiaoqu> xiaoqus = new ArrayList<>();
            towerList = SaveOrDeletePrefrence.getList(getApplicationContext(), homeList.get(i) + "tower");
            alltowers.addAll(towerList);
            cardList = SaveOrDeletePrefrence.getList(getApplicationContext(), homeList.get(i) + "card");
            allCards.addAll(cardList);
            elevatorNumbers = SaveOrDeletePrefrence.getList(getApplicationContext(), homeList.get(i) + "elevatorNumber");
            allNumbers.addAll(elevatorNumbers);
            wifiPwList = SaveOrDeletePrefrence.getList(getApplicationContext(), homeList.get(i) + "wifi");
            allwifis.addAll(wifiPwList);
            for (int j = 0; j < towerList.size(); j++) {

                SaveOrDeletePrefrence.save(getApplicationContext(), towerList.get(j) + "card", cardList.get(j));
                SaveOrDeletePrefrence.save(getApplicationContext(), towerList.get(j) + "elevator", elevatorNumbers.get(j));
                SaveOrDeletePrefrence.save(getApplicationContext(), towerList.get(j) + "wifi", wifiPwList.get(j));

                user1 = new User(wifiPwList.get(j), towerList.get(j), elevatorNumbers.get(j));
                Kabao kabao2 = null;
                List<Kabao> kabaos = new ArrayList<>();
                powerNameList = SaveOrDeletePrefrence.getList(getApplicationContext(), towerList.get(j));
                allPowers.add(powerNameList);
                Log.d("gaotuo", "powerNameList:" + powerNameList);
                for (int k = 0; k < powerNameList.size(); k++) {
                    kabao2 = new Kabao(powerNameList.get(k));
                    kabaos.add(kabao2);
                }
                //xiaoqu = new Xiaoqu(kabaos, user1, cardList.get(j));
                //xiaoqus.add(xiaoqu);
            }
           /* Log.d("TAG1", "卡包集合:" + cardList);
            Log.d("TAG1", "towerList:" + towerList);
            Log.d("TAG1", "number集合:" + elevatorNumbers);
            Log.d("TAG1", "wifi集合:" + wifiList);*/
            //allCard = new AllCard(xiaoqus);
           // allCardList.add(allCard);
        }
       // Log.d("TAG1", "总小区:" + allCardList);
    }

    private void initNotice() {
        List<String> companyIds = SaveOrDeletePrefrence.getList(getApplicationContext(), "companyId");

        if (companyIds == null) {
            return;
        }

        for (int i = 0; i < companyIds.size(); i++) {
            List<String> paths = SaveOrDeletePrefrence.getList(getApplicationContext(), companyIds.get(i) + "path");
            for (int j = 0; j < paths.size(); j++) {
                if (j > 2) {
                    break;
                }
                imageUris[j] = paths.get(j);
            }
        }
    }


    private void initView() {

        /*home = getIntent().getStringExtra("home");
        tower = getIntent().getStringExtra("tower");*/
        //测试数据
        initList();
        initNotice();

        drawerlayout = findViewById(R.id.drawerlayout);
        gengduo = findViewById(R.id.gengduo);
        shuaxin = findViewById(R.id.shuaxin);
        genggaiAnniu = findViewById(R.id.genggai_anniu);
        recycleView = findViewById(R.id.recycleView);
        navView = findViewById(R.id.nav_view);
        tvLouhao = findViewById(R.id.tv_louhao);
        nav_view = navView.getHeaderView(0);
        tv_name = nav_view.findViewById(R.id.tv_nav_name);
        tv_phone = nav_view.findViewById(R.id.tv_nav_phone);
        alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.dialog);

        //tvLouhao.setText(tower);

        int size = initSize();
        initTextViews(size);
        initViewPager();


        phoneNumber = SaveOrDeletePrefrence.look(getApplicationContext(), "phone");
        name = SaveOrDeletePrefrence.look(getApplicationContext(), "name");
        byte[] phoneByte = StrUtil.hexStringToByte("0" + phoneNumber);
        user.setUID(phoneByte);

        tv_phone.setText(StrUtil.setPhone(phoneNumber));
        tv_name.setText(name);

        navView.setNavigationItemSelectedListener(new MyNavigationItemSelectedListener());
    }


    class MyNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_camera:
                    Log.d("tag", "import");
                    Toast.makeText(MainActivity.this, "功能未开通", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_slideshow:
                    if (isVoice) {
                        item.setTitle("打开提示音");
                        isVoice = false;
                    } else {
                        item.setTitle("关闭提示音");
                        isVoice = true;
                    }
                    break;
                case R.id.nav_manage:

                    Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                    intent.putExtra("title", "关于");
                    startActivity(intent);
                    break;

                case R.id.nav_exit:
                    //wa.disconnectWifi(wa.getNetworkId());
                    wa.removeWifiBySsid("\"" + wifiSsid + "\"");
                    SaveOrDeletePrefrence.deleteAll(getApplicationContext());
                    Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                    /*ActivityCollector.finishAll();
                    System.exit(0);*/
                    break;
                default:
                    break;
            }
            return true;
        }
    }


    private void initPoint() {
        points = new ImageView[imageUris.length];
        //全部圆点设置为未选中状态
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setBackgroundResource(R.drawable.nomal);
        }
        //currentItem = 0;
        points[currentItem % imageUris.length].setBackgroundResource(R.drawable.select);
    }

    //设置当前的点
    private void setCurrentPoint(int position) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setBackgroundResource(R.drawable.nomal);
        }
        points[position].setBackgroundResource(R.drawable.select);
    }

    //初始化viewPager
    private void initViewPager() {
        linearLayout = findViewById(R.id.viewPager_lineatLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(imageUris, simpleDraweeViews, new ViewPagerAdapter.ViewPagerOnClick() {
            @Override
            public void onClick(int posotion, String url) {
                Log.d("viewPager", "ViewPager点击事件： " + posotion + " ; " + url);
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                //intent.putExtra("imageUrl", url);//轮播图点击事件
                startActivity(intent);
            }
        });

        viewPager.setAdapter(viewPagerAdapter);
        initPoint();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                setCurrentPoint(position % imageUris.length);
                startAutoScroll();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        currentItem = imageUris.length * 1000; //取一个中间的大数字，防止接近边界
        viewPager.setCurrentItem(currentItem);
    }

    private int initSize() {
        int size;
        if (imageUris.length > 3) {
            size = imageUris.length;
        } else {
            size = imageUris.length * 2; // 小于3个时候, 需要扩大一倍, 防止出错
        }
        return size;
    }

    private void initTextViews(int size) {
        SimpleDraweeView[] tvs = new SimpleDraweeView[size];

        for (int i = 0; i < tvs.length; i++) {
            tvs[i] = new SimpleDraweeView(this);
            tvs[i].getHierarchy().setPlaceholderImage(R.drawable.vp_gaotuo);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tvs[i].setLayoutParams(layoutParams);
            simpleDraweeViews = tvs;
        }
    }

    private boolean wifi = false;

    //wifi广播接收器
    class WifiReceiver extends BroadcastReceiver {
        private static final String TAG = "wifiReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
                Log.i(TAG, "wifi信号强度变化");
            }

            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {

                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                    wifi = true;
                    Log.i(TAG, "wifi断开");
                } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    Log.i(TAG, "连接到网络 " + wifiInfo.getSSID());
                    if (wifi) {
                        handler.sendEmptyMessage(0);
                        wifi = false;
                    }
                }
            }

            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
                if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                    Log.i(TAG, "系统关闭wifi");

                } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                    Log.i(TAG, "系统打开wifi");
                    wifi = true;
                }
            }
        }
    }

    class MyThread extends Thread {
        private Socket socket = null;
        private OutputStream ou;
        private InputStream in;
        public int DEV_START = 0;
        public int DEV_GETR = 1;
        public int DEV_CONTROL = 2;
        public int DEV_CLOSE = 3;

        public boolean waitret = false;
        public int ctrlstatus = DEV_START;

        public int t = 0;

        Message msg;
        Bundle bundle = new Bundle();

        int i = 0, CMD;
        String pstr = "";

        public MyThread(int cmd, String str) {
            CMD = cmd;
            pstr = str;

            bundle.clear();

            if (ctrlBusy == false) {
                ctrlstatus = DEV_START;
                ctrlBusy = true;
            } else {
                bundle.putString("msg", "正忙");
                msg = new Message();
                msg.setData(bundle);
                msg.what = 0x44;
                handler.sendMessage(msg);
                ctrlstatus = DEV_CLOSE;
            }
        }

        @Override
        public void run() {

            while (true) {
                if (ctrlstatus == DEV_START) {
                    try {
                        socket = new Socket();
                        socket.connect(new InetSocketAddress("192.168.4.1", 1039), 5000);
                        ou = socket.getOutputStream();
                        in = socket.getInputStream();

                        if (CMD == 2 || CMD == 3 || CMD == 4 || CMD == 6) {
                            ctrlstatus = DEV_GETR;
                        } else {
                            ctrlstatus = DEV_CONTROL;
                        }
                        waitret = false;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        ctrlstatus = DEV_CLOSE;
                    }
                    continue;
                } else if (ctrlstatus == DEV_GETR) {
                    try {
                        if (waitret == false) {
                            user.setdate(1, pstr, random);
                            ou.write(user.sendBuf, 0, 11);

                            ou.flush();
                            waitret = true;
                        } else {
                            if (in.read() != 0xBB) {
                                t++;
                                if (t > 50) {
                                    ctrlBusy = false;
                                    ctrlstatus = DEV_CLOSE;
                                    //Log.d("tag2", "t = " + t);
                                }
                                continue;
                            }
                            i = in.read(readBuf, 0, 14);
                            if (i < 14) {
                                //continue;
                            }
                            random[0] = readBuf[8];
                            random[1] = readBuf[9];
                            random[2] = readBuf[10];
                            random[3] = readBuf[11];

                            ctrlstatus = DEV_CONTROL;
                            waitret = false;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        ctrlstatus = DEV_CLOSE;
                    }
                } else if (ctrlstatus == DEV_CONTROL) {
                    try {
                        if (waitret == false) {
                            if (CMD == 4) {
                                if (isVoice)
                                    mediaPlayerOk.start();
                                user.setdate(4, pstr, random);
                                ou.write(user.sendBuf, 0, 47);
                                ou.flush();
                            } else if (CMD == 7) {
                                user.setdate(7, pstr, random);
                                ou.write(user.sendBuf, 0, 17);
                                ou.flush();
                            } else if (CMD == 8) {
                                user.setdate(8, pstr, random);
                                ou.write(user.sendBuf, 0, 18);
                                ou.flush();
                            }
                            waitret = true;
                        } else {

                            if (in.read() != 0xBB) {
                                t++;
                                if (t > 50) {
                                    ctrlBusy = false;
                                    ctrlstatus = DEV_CLOSE;
                                }
                                continue;
                            }

                            i = in.read(readBuf, 0, 50);

                            if (i < 11) {

                            }
                            ctrlstatus = DEV_CLOSE;
                            waitret = false;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        ctrlstatus = DEV_CLOSE;
                    }
                    continue;
                } else if (ctrlstatus == DEV_CLOSE) {
                    try {
                        if (in != null) {
                            in.close();
                            ou.close();
                            socket.close();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    ctrlBusy = false;
                    break;
                }
            }
        }
    }
}