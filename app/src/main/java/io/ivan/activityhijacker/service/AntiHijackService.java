package io.ivan.activityhijacker.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.ivan.activityhijacker.R;

public class AntiHijackService extends Service {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final Class<? extends Context> START_ACTIVITY = Activity.class;
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isStart = false;
    private Notification notification;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            List<AndroidAppProcess> runningForegroundApps = AndroidProcesses.getRunningForegroundApps(AntiHijackService.this);
            if (runningForegroundApps.size() > 0) {
                if (!runningForegroundApps.get(0).getPackageName().equals(getPackageName())) {
                    if (notification == null) {
                        showClickableNotification(START_ACTIVITY);
                    }
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        super.onStartCommand(intent, flag, startId);
        if (!isStart) {
            isStart = true;
            new Timer().schedule(timerTask, 0, 1000);
        }
        stopSelf();
        return START_STICKY;
    }

    private void showClickableNotification(Class<? extends Context> clazz) {
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(clazz)
                .addNextIntent(new Intent(this, clazz))
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(this, getPackageName())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("警告！！！")
                .setContentText(getResources().getString(R.string.app_name) + "你访问的页面可能被劫持。")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(1, notification);
        }
    }

} 