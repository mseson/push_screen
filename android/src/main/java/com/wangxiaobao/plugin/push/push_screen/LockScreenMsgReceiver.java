package com.wangxiaobao.plugin.push.push_screen;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;


/**
 * 监听锁屏消息的广播接收器
 */
public class LockScreenMsgReceiver extends BroadcastReceiver {
    private static final String TAG = "LockScreenMsgReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:收到了锁屏消息 ");
        String action = intent.getAction();
        if (action.equals("com.demo.lockscreenmsgdemo.LockScreenMsgReceiver")) {
            //管理锁屏的一个服务
            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            String text = km.inKeyguardRestrictedInputMode() ? "锁屏了" : "屏幕亮着的";
            Log.i(TAG, "text: " + text);
            if (km.inKeyguardRestrictedInputMode()) {
                Log.i(TAG, "onReceive:锁屏了 ");

                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                if (!pm.isScreenOn()) {
                    Log.i(TAG, "onReceive:电源点亮屏幕");
                    @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
                    //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
                    wl.acquire();  //点亮屏幕
                }
            }
            //判断是否锁屏
            Intent alarmIntent = new Intent(context, TestActivity.class);
            //在广播中启动Activity的context可能不是Activity对象，所以需要添加NEW_TASK的标志，否则启动时可能会报错。
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(Build.VERSION.SDK_INT >= 26){
                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            }

            context.startActivity(alarmIntent); //启动显示锁屏消息的activity
        }
    }
}
