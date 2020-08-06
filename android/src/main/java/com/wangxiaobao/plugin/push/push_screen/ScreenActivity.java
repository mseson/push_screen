package com.wangxiaobao.plugin.push.push_screen;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenActivity extends Activity {

    private ImageView headImg;
    private TextView nameText;
    private ImageView ignore;
    private ImageView vr;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;
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
            }
        });

        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //带看
            }
        });
    }

    private void initData() {
        String name = getIntent().getStringExtra("");
        String headImg = getIntent().getStringExtra("");
        nameText.setText(name);
    }
}