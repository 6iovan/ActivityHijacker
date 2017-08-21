package io.ivan.activityhijacker;


import android.content.Context;
import android.content.Intent;

import io.ivan.activityhijacker.service.HijackingService;

public class Hijack {

    public static void start(Context context, String packageName4hiJack) {
        Intent intent = new Intent(context, HijackingService.class);
        intent.putExtra(HijackingService.U_WANT_TO_HIJACK, packageName4hiJack);
        context.startService(intent);
    }

    public static void start(Context context, String packageName4hiJack, boolean isHijackOnce) {
        Intent intent = new Intent(context, HijackingService.class);
        intent.putExtra(HijackingService.U_WANT_TO_HIJACK, packageName4hiJack);
        intent.putExtra(HijackingService.HIJACK_ONLY_ONE, isHijackOnce);
        context.startService(intent);
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, HijackingService.class));
    }

}
