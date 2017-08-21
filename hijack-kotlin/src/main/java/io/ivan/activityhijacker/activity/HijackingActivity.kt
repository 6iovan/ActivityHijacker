package io.ivan.activityhijacker.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.ivan.activityhijacker.Hijack
import io.ivan.activityhijacker.R
import kotlinx.android.synthetic.main.activity_hijacking.*

class HijackingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hijacking)
        btn.setOnClickListener {
            Hijack.stop(this)
            finish()
        }
    }

}
