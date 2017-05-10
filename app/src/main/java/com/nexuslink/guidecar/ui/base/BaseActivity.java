package com.nexuslink.guidecar.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nexuslink.guidecar.util.SpUtil;


/**
 * 基础activity
 * Created by Rye on 2017/5/10.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SpUtil.getThemeId());
    }
}

