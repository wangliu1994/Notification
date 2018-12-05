package com.winnie.utils.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;


/**
 * @author winnie
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void systemNotify(View view) {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("系统通知")
                .setContentText("这是系统通知，你惊讶吗？");

        compatibleVersonO(manager, builder);
        manager.notify(0, builder.build());
    }

    public void systemNotify1(View view) {
        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification);
        Notification.Builder builder = new Notification.Builder(
                MainActivity.this).setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("5 new message")
                .setContentText("twain@android.com");
        //第一次提示消息的时候显示在通知栏上
        builder.setTicker("New message");
        builder.setNumber(12);
        builder.setLargeIcon(btm);
        //自己维护通知的消失
        builder.setAutoCancel(true);
        //构建一个Intent
        Intent resultIntent = new Intent(MainActivity.this, Main2Activity.class);

        //封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                MainActivity.this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        builder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        compatibleVersonO(manager, builder);
        manager.notify(1, builder.build());
    }

    public void systemNotify2(View view) {
        Bitmap btm = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notification);
        Intent intent = new Intent(MainActivity.this,
                Main2Activity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                MainActivity.this, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                MainActivity.this)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(btm)
                .setNumber(13)
                .setContentIntent(pendingIntent)
                .setStyle(
                        new NotificationCompat.InboxStyle()
                                .addLine(
                                        "M.Twain (Google+) Haiku is more than a cert...")
                                .addLine("M.Twain Reminder")
                                .addLine("M.Twain Lunch?")
                                .addLine("M.Twain Revised Specs")
                                .addLine("M.Twain ")
                                .addLine(
                                        "Google Play Celebrate 25 billion apps with Goo..")
                                .addLine(
                                        "Stack Exchange StackOverflow weekly Newsl...")
                                .setBigContentTitle("6 new message")
                                .setSummaryText("mtwain@android.com"));

        NotificationManager manager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        compatibleVersonO(manager, builder);
        manager.notify(2, builder.build());
    }

    public void progressNotify(View view) {
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        final Notification.Builder builder = new Notification.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Picture Download")
                .setContentText("Download in progress");
        builder.setAutoCancel(true);
        compatibleVersonO(manager, builder);
        //通过一个子线程，动态增加进度条刻度
        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr;
                for (incr = 0; incr <= 100; incr += 5) {
                    builder.setProgress(100, incr, false);
                    manager.notify(3, builder.build());
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Log.i("TAG", "sleep failure");
                    }
                }
                builder.setContentText("Download complete")
                        .setProgress(0, 0, false);
                manager.notify(3, builder.build());
            }
        }).start();
    }

    public void progressNotify1(View view) {
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Picture Download")
                .setContentText("Download in progress");
        //设置为true，表示流动
        builder.setProgress(0, 0, true);
        compatibleVersonO(manager, builder);
        manager.notify(4, builder.build());
        //5秒之后还停止流动
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //设置为false，表示刻度
                builder.setProgress(100, 100, false);
                manager.notify(4, builder.build());
            }
        }).start();
    }

    public void customNotify(View view) {
        final RemoteViews contentViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        contentViews.setImageViewResource(R.id.iv_notify, R.drawable.ic_notification);
        contentViews.setTextViewText(R.id.tv_notify_title, "开始下载");
        contentViews.setTextViewText(R.id.tv_notify_content, "进度0%");

        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                MainActivity.this, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("My notification")
                .setTicker("new message");

        builder.setContentIntent(pendingIntent);
        builder.setContent(contentViews);
        builder.setAutoCancel(true);
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        compatibleVersonO(manager, builder);
        final Notification notification = builder.build();
        manager.notify(5, notification);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr;
                for (incr = 0; incr <= 100; incr += 5) {
                    if (incr < 100) {
                        contentViews.setTextViewText(R.id.tv_notify_content, String.format("进度%1$s%2$s", incr, "%"));
                    } else {
                        contentViews.setTextViewText(R.id.tv_notify_title, "下载完成");
                        contentViews.setTextViewText(R.id.tv_notify_content, "进度100%");
                    }
                    manager.notify(5, notification);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Log.i("TAG", "sleep failure");
                    }
                }
            }
        }).start();
    }

    //TODO 声音，震动，闪烁在小米手机上未得到预期效果
    public void soundNotify(View view) {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, getPackageName())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("8.0系统通知")
                    .setContentText("这是8.0系统通知，你惊讶吗？");
            NotificationChannel channel = new NotificationChannel(getPackageName(),
                    "TAG", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6"), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            manager.createNotificationChannel(channel);
            Notification notification = builder.build();
            manager.notify(6, notification);
        } else {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("系统通知")
                    .setContentText("这是系统通知，你惊讶吗？");
            Notification notification = builder.build();
            notification.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6");
            manager.notify(6, notification);
        }
    }

    public void soundNotify1(View view) {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String sChannelId = "0x02";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(sChannelId,
                    "TAG", NotificationManager.IMPORTANCE_HIGH);
            //是否在桌面icon右上角展示小红点
            channel.enableLights(false);
            //是否在久按桌面图标时显示此渠道的通知
            channel.setShowBadge(false);
            channel.enableVibration(true);
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            channel.setVibrationPattern(new long[]{0, 300, 300, 300});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, sChannelId);
        builder.setContentTitle(getResources().getString(R.string.app_name));
        builder.setContentText("系统通知");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_notification);

        Intent resultIntent = new Intent(this, Main2Activity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        Notification notification = builder.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification.vibrate = new long[]{0, 300, 300, 300};
        assert notificationManager != null;
        notificationManager.notify(1, notification);
    }

    public void vibrateNotify(View view) {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, getPackageName())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("8.0系统通知")
                    .setContentText("这是8.0系统通知，你惊讶吗？");
            NotificationChannel channel = new NotificationChannel(getPackageName(),
                    "TAG", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 100, 200, 300});
            manager.createNotificationChannel(channel);
            Notification notification = builder.build();
            manager.notify(8, notification);
        } else {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("系统通知")
                    .setContentText("这是系统通知，你惊讶吗？");
            Notification notification = builder.build();
//            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.vibrate = new long[]{0, 100, 200, 300};
            manager.notify(8, notification);
        }
    }

    public void lightNotify(View view) {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("系统通知")
                .setContentText("这是系统通知，你惊讶吗？");

        compatibleVersonO(manager, builder);
        Notification notification = builder.build();
//        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.ledARGB = 0xff00ff00;
        notification.ledOnMS = 300;
        notification.ledOffMS = 1000;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        manager.notify(9, notification);
    }

    /**
     * 8.0以上的兼容操作
     *
     * @param manager
     * @param builder
     */
    private void compatibleVersonO(NotificationManager manager, Notification.Builder builder) {
        // 此处必须兼容android O设备，否则系统版本在O以上可能不展示通知栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(getPackageName(),
                    "TAG", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            // channelId非常重要，不设置通知栏不展示
            builder.setChannelId(getPackageName());
        }
    }

    private void compatibleVersonO(NotificationManager manager, NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(getPackageName());
        }
    }

}
