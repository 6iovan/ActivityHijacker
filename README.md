# ActivityHijacker
Hijack and AntiHijack for Android activity.

but not work on Android 7.0


# Usage


## Hijack
Modify the constant in the HijackingService.kt
```
////////////////////////////////////////////////////////////////////////////////////////////////////
const val U_WANT_TO_HIJACK = "com.google.android.youtube"
const val HIJACK_ONLY_ONE = false
////////////////////////////////////////////////////////////////////////////////////////////////////
```


## Anti Hijack

Copy the AntiHijackService.java in you App.

Modify the constant
```
////////////////////////////////////////////////////////////////////////////////////////////////
private static final Class<? extends Context> START_ACTIVITY = null;
////////////////////////////////////////////////////////////////////////////////////////////////
```

In Application:
```
registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated() called with: activity = [" + activity + "], savedInstanceState = [" + savedInstanceState + "]");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted() called with: activity = [" + activity + "]");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "onActivityResumed() called with: activity = [" + activity + "]");
                Intent intent = new Intent(activity, AntiHijackService.class);
                stopService(intent);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
                Intent intent = new Intent(activity, AntiHijackService.class);
                startService(intent);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
                Intent intent = new Intent(activity, AntiHijackService.class);
                stopService(intent);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(TAG, "onActivitySaveInstanceState() called with: activity = [" + activity + "], outState = [" + outState + "]");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
            }
        });
```


# Thanks
[AndroidProcesses](https://github.com/jaredrummler/AndroidProcesses)