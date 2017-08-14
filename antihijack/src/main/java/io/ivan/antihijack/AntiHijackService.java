package io.ivan.antihijack;

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

public class AntiHijackService extends Service {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final Class<? extends Context> START_ACTIVITY = null;
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isStart = false;
    private NotificationCompat.Builder builder;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            List<AndroidAppProcess> runningForegroundApps = AndroidProcesses.getRunningForegroundApps(AntiHijackService.this);
            if (runningForegroundApps.size() > 0) {
                if (!runningForegroundApps.get(0).getPackageName().equals(getPackageName())) {
                    if (builder == null) {
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
        builder = new NotificationCompat.Builder(this, getPackageName())
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle("警告！！！")
                .setContentText("您当前访问的页面可能被劫持。")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        if (clazz != null) {
            PendingIntent pendingIntent = TaskStackBuilder.create(this)
                    .addParentStack(clazz)
                    .addNextIntent(new Intent(this, clazz))
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(1, builder.build());
        }
    }

} 