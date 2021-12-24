package com.example.hook.hooktest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_getDevice).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                TelephonyManager tl = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                tl.getDeviceId();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tl.getMeid();
                }
            }
        });
    }
}
