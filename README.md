# DEPRECATED

## ActivityHijacker
Hijack and AntiHijack for Android activity.

Not work on Android7.0 or later.
Because you have to use UsageStatsManager or have root access on Android7.0, So Android7.0 is safer than ever!


## Usage

### Hijack

```java
Hijack.start(this, "com.tencent.mm");

Hijack.start(this, "com.tencent.mm", true);

Hijack.stop(HijackingActivity.this);
```

### Anti Hijack

In Application:

```java
@Override
public void onCreate() {
    super.onCreate();
    AntiHijack.init(this);
    AntiHijack.setNotificationContent(HomeActivity.class);
}
```

OR

In BaseActivity:

```java
@Override
protected void onResume() {
    super.onResume();
    AntiHijack.onResume();
}
```

```java
@Override
protected void onPause() {
    super.onPause();
    AntiHijack.onPause(this);
}
```


## Thanks
[AndroidProcesses](https://github.com/jaredrummler/AndroidProcesses)
