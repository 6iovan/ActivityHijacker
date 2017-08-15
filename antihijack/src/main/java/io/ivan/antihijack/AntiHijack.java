package io.ivan.antihijack;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

public class AntiHijack {

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
                activity.startService(new Intent(activity, AntiHijackService.class));
            }

            @Override
            public void onActivityPaused(Activity activity) {
                activity.stopService(new Intent(activity, AntiHijackService.class));
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

}
