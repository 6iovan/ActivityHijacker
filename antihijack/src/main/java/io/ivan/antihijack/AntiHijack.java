package io.ivan.antihijack;


import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AntiHijack {

    private static Class<? extends Context> where2go = null;

    private static List<Boolean> isSelf = new ArrayList<>();

    public static void init(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                AntiHijack.onResume();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                AntiHijack.onPause(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static void onResume() {
        int size = isSelf.size();
        if (size > 0) {
            isSelf.set(size - 1, true);
            isSelf.remove(size - 1);
        }
    }

    public static void onPause(final Activity activity) {
        isSelf.add(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = isSelf.size();
                if (size > 0) {
                    if (!isSelf.get(size - 1)) {
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                List<AndroidAppProcess> runningForegroundApps = AndroidProcesses.getRunningForegroundApps(activity);
                                if (runningForegroundApps.size() > 0) {
                                    String foregroundAppPackageName = runningForegroundApps.get(0).getPackageName();
                                    if (!foregroundAppPackageName.equals(activity.getPackageName())) {
                                        showClickableNotification(activity, where2go);
                                        cancel();
                                    }
                                }
                            }
                        };
                        new Timer(true).schedule(timerTask, 0, 500);
                    }
                }
            }
        }, 1500);
    }

    public static void setNotificationContent(Class<? extends Context> clazz) {
        where2go = clazz;
    }

    private static void showClickableNotification(Context context, Class<? extends Context> clazz) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getPackageName())
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("警告！！！")
                .setContentText("您当前访问的页面可能被劫持。")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        if (clazz != null) {
            PendingIntent pendingIntent = TaskStackBuilder.create(context)
                    .addParentStack(clazz)
                    .addNextIntent(new Intent(context, clazz))
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }


}
