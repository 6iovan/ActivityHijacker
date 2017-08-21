package io.ivan.activityhijacker


import android.content.Context
import android.content.Intent
import io.ivan.activityhijacker.service.HijackingService

object Hijack {

    fun start(context: Context, packageName4hiJack: String) {
        val intent = Intent(context, HijackingService::class.java)
        intent.putExtra(HijackingService.U_WANT_TO_HIJACK, packageName4hiJack)
        context.startService(intent)
    }

    fun start(context: Context, packageName4hiJack: String, isHijackOnce: Boolean) {
        val intent = Intent(context, HijackingService::class.java)
        intent.putExtra(HijackingService.U_WANT_TO_HIJACK, packageName4hiJack)
        intent.putExtra(HijackingService.HIJACK_ONLY_ONE, isHijackOnce)
        context.startService(intent)
    }

    fun stop(context: Context) {
        context.stopService(Intent(context, HijackingService::class.java))
    }

}
