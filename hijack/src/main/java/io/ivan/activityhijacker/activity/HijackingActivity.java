package io.ivan.activityhijacker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.ivan.activityhijacker.Hijack;
import io.ivan.activityhijacker.R;

public class HijackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijacking);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hijack.stop(HijackingActivity.this);
                finish();
            }
        });
    }

}
