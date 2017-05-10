package com.nexuslink.guidecar.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.nexuslink.guidecar.R;
import com.nexuslink.guidecar.ui.base.BaseActivity;
import com.nexuslink.guidecar.util.SpUtil;
import com.nexuslink.guidecar.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.night_mode_switch)
    Switch switchNight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        switchNight.setChecked(SpUtil.getThemeId() == R.style.NightTheme);
        switchNight.setOnClickListener(this);

    }

    //主题切换
    @Override
    public void onClick(View view) {
        if(SpUtil.getThemeId() == R.style.DayTheme){
            ToastUtil.shortToast("切换为夜间模式");
            SpUtil.saveThemeId(R.style.NightTheme);
        }else {
            ToastUtil.shortToast("切换为白天模式");
            SpUtil.saveThemeId(R.style.DayTheme);
        }
        recreate();
    }

    //设置resultCode
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
