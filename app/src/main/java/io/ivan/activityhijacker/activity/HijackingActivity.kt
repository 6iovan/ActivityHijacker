package io.ivan.activityhijacker.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.ivan.activityhijacker.R
import io.ivan.activityhijacker.service.HijackingService
import kotlinx.android.synthetic.main.activity_hijacking.*

class HijackingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hijacking)
        btn.setOnClickListener {
            stopService(Intent(this, HijackingService::class.java))
            finish()
        }
    }

}
