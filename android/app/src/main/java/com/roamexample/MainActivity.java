package com.roamexample;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.roam.sdk.Roam;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "RoamExample";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Roam.notificationOpenedHandler(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, RoamForegroundService.class));
    }
}
