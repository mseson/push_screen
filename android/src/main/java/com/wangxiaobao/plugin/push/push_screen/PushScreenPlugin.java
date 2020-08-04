package com.wangxiaobao.plugin.push.push_screen;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.NonNull;
import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** PushScreenPlugin */
public class PushScreenPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    private static final String TAG = "LockScreenMsgReceiver";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        context = flutterPluginBinding.getApplicationContext();
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "push_screen");
        channel.setMethodCallHandler(this);
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "push_screen");
        channel.setMethodCallHandler(new PushScreenPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            Log.d("native : ","getPlatformVersion");
            result.success("Android " + android.os.Build.VERSION.RELEASE);
            //启动后台服务
//            Intent intent = new Intent(mContext, MyService.class);
//            mContext.startService(intent);

//            Intent intent = new Intent();
//            intent.setAction("com.wangxiaobao.plugin.push.push_screen.LockScreenMsgReceiver");
//            //Android O版本对后台进程做了限制广播的发送，对隐式广播也做了限制；必须要指定接受广播类的包名和类名
//            //解决Background execution not allowed-----8.0以上发送的隐式广播无法被收到问题
//            intent.setComponent(new ComponentName("com.wangxiaobao.plugin.push.push_screen_example","com.wangxiaobao.plugin.push.push_screen.LockScreenMsgReceiver"));
//            mContext.sendBroadcast(intent); //发
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                    String text = km.inKeyguardRestrictedInputMode() ? "锁屏了" : "屏幕亮着的";
                    android.util.Log.i(TAG, "text: " + text);
                    if (km.inKeyguardRestrictedInputMode()) {
                        android.util.Log.i(TAG, "onReceive:锁屏了 ");

                        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                        if (!pm.isScreenOn()) {
                            android.util.Log.i(TAG, "onReceive:电源点亮屏幕");
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

                    context.startActivity(alarmIntent); //
                }
            }).start();


        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}