package com.wangxiaobao.plugin.push.push_screen;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 模拟推送，在退出APP后的一段时间发送消息
 */
public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        sendMessage();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }


    /**
     * 模仿推送，发消息
     */
    private void sendMessage() {
        System.out.println("sendMessage");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setAction("com.wangxiaobao.plugin.push.push_screen.LockScreenMsgReceiver");
                //Android O版本对后台进程做了限制广播的发送，对隐式广播也做了限制；必须要指定接受广播类的包名和类名
                //解决Background execution not allowed-----8.0以上发送的隐式广播无法被收到问题
                intent.setComponent(new ComponentName("com.wangxiaobao.plugin.push.push_screen","com.wangxiaobao.plugin.push.push_screen.LockScreenMsgReceiver"));
                sendBroadcast(intent); //发送广播
            }
        }).start();
    }
}
