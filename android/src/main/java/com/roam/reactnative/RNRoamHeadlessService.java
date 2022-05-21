package com.roam.reactnative;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class RNRoamHeadlessService extends HeadlessJsTaskService {

    @Nullable
    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extra = intent.getExtras();
        return new HeadlessJsTaskConfig(
                "RoamHeadlessService",
                extra != null ? Arguments.fromBundle(extra) : Arguments.createMap(),
                0,
                true
        );
    }
}
