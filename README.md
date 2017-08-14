# ActivityHijacker
Hijack and AntiHijack for Android activity.

Not work on Android7.0 or later.
Because you have to use UsageStatsManager or have root access on Android7.0, So Android7.0 is safer than ever!

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
@Override
public void onCreate() {
    super.onCreate();
    AntiHijack.init(this);
}
```


# Thanks
[AndroidProcesses](https://github.com/jaredrummler/AndroidProcesses)
