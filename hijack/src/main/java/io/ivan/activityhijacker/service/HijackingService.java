package io.ivan.activityhijacker.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.ivan.activityhijacker.activity.HijackingActivity;

public class HijackingService extends Service {

    public static final String U_WANT_TO_HIJACK = "packageName4hiJack";
    public static final String HIJACK_ONLY_ONE = "isHijackOnce";

    private Timer timer = new Timer();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String packageName4hiJack = intent.getStringExtra(U_WANT_TO_HIJACK);
        final boolean isHijackOnce = intent.getBooleanExtra(HIJACK_ONLY_ONE, false);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                List<AndroidAppProcess> runningForegroundApps =
                        AndroidProcesses.getRunningForegroundApps(HijackingService.this);
                if (runningForegroundApps.size() > 0) {
                    String foregroundAppPackageName = runningForegroundApps.get(0).getPackageName();
                    if (foregroundAppPackageName.equals(packageName4hiJack)) {
                        if (isHijackOnce) {
                            hijacking();
                            timer.cancel();
                        } else {
                            hijacking();
                        }
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 500);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void hijacking() {
        Intent intent = new Intent(this, HijackingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
