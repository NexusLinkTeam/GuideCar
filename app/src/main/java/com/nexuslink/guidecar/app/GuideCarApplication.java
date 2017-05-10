package com.nexuslink.guidecar.app;

import android.app.Application;
import android.content.Context;

/**
 * Application
 * Created by Rye on 2017/5/10.
 */

public class GuideCarApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
