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

    private TimerTask timerTask;
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        super.onStartCommand(intent, flag, startId);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                List<AndroidAppProcess> runningForegroundApps = AndroidProcesses.getRunningForegroundApps(AntiHijackService.this);
                if (runningForegroundApps.size() > 0) {
                    if (!runningForegroundApps.get(0).getPackageName().equals(getPackageName())) {
                        showClickableNotification(START_ACTIVITY);
                        stopSelf();
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 500);
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void showClickableNotification(Class<? extends Context> clazz) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getPackageName())
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
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

} 