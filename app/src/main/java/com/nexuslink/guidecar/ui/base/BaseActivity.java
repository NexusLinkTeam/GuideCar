package com.nexuslink.guidecar.ui.base;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.clj.fastble.BleManager;
import com.nexuslink.guidecar.util.BleManagerUtil;
import com.nexuslink.guidecar.util.SpUtil;


/**
 * 基础activity
 * Created by Rye on 2017/5/10.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置主题
        setTheme(SpUtil.getThemeId());
    }

    public BleManager getBleManager() {
        return BleManagerUtil.getInstance();
    }
}

