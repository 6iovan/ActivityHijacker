package io.ivan.activityhijacker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.ivan.activityhijacker.Hijack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hijack.start(this, "com.tencent.mm");
        finish();
    }

}
