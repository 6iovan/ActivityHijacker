package io.ivan.activityhijacker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.jaredrummler.android.processes.AndroidProcesses
import io.ivan.activityhijacker.activity.HijackingActivity
import java.util.*
import kotlin.concurrent.timerTask

class HijackingService : Service() {

    companion object {
        const val U_WANT_TO_HIJACK = "packageName4hiJack"
        const val HIJACK_ONLY_ONE = "isHijackOnce"
    }

    private val timer by lazy { Timer() }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val packageName4hiJack = intent.getStringExtra(U_WANT_TO_HIJACK)
        val isHijackOnce = intent.getBooleanExtra(HIJACK_ONLY_ONE, false)
        val task = timerTask {
            AndroidProcesses.getRunningForegroundApps(this@HijackingService)
                    .asSequence()
                    .filter { packageName4hiJack == it.packageName }
                    .forEach {
                        if (isHijackOnce) {
                            hijacking()
                            timer.cancel()
                        } else {
                            hijacking()
                        }
                    }
        }
        timer.schedule(task, 0, 500)
        return super.onStartCommand(intent, flags, startId)
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

