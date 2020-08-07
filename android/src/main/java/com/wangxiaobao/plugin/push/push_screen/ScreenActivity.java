package com.wangxiaobao.plugin.push.push_screen;


import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import io.flutter.app.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StringCodec;


public class ScreenActivity extends FlutterActivity {

    private static BasicMessageChannel chanel;
    private static final String CHANNEL_NATIVE = "com.wxb.flutter/screen";
    private FlutterEngine flutterEngine;


    private ImageView headImg;
    private TextView nameText;
    private ImageView ignore;
    private ImageView vr;
    private Context mContext;


    String project;
    String cardInfo;
    String vrModel;
    String roomId;
    int terminal;
    String projectAccount;
    String vrTitle;
    String clientId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //四个标志位顾名思义，分别是锁屏状态下显示，解锁，保持屏幕长亮，打开屏幕。这样当Activity启动的时候，它会解锁并亮屏显示。
        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //锁屏状态下显示
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD //解锁
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕长亮
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON); //打开屏幕
        //使用手机的背景
        Drawable wallPaper = WallpaperManager.getInstance(this).getDrawable();
        win.setBackgroundDrawable(wallPaper);

        setContentView(R.layout.activity_screen);
        initView();
        initData();
    }

    private void initView() {
        headImg = findViewById(R.id.head_image_id);
        nameText = findViewById(R.id.name_text_id);
        ignore = findViewById(R.id.left_image_id);
        vr = findViewById(R.id.right_image_id);
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //忽略
                finish();
            }
        });

        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //带看
                if (chanel!=null){
                    chanel.send("VR带看");
                }
            }
        });


        flutterEngine = new FlutterEngine(this);
        flutterEngine.getNavigationChannel().setInitialRoute("route1");
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );

    }

    private void initData() {
        project = getIntent().getStringExtra("project");
        cardInfo = getIntent().getStringExtra("cardInfo");
        vrModel = getIntent().getStringExtra("vrModel");
        roomId = getIntent().getStringExtra("roomId");
        terminal = getIntent().getIntExtra("terminal", -1);
        projectAccount = getIntent().getStringExtra("projectAccount");
        vrTitle = getIntent().getStringExtra("vrTitle");
        clientId = getIntent().getStringExtra("clientId");
        chanel = new BasicMessageChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NATIVE, StringCodec.INSTANCE);
    }
}