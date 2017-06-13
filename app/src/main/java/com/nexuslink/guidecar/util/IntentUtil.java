package com.nexuslink.guidecar.util;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.nexuslink.guidecar.ui.base.BaseActivity;

/**
 * intent的简单封装（不算封装吧）
 * Created by Rye on 2017/5/10.
 */

public class IntentUtil {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void simpleStartActivity(BaseActivity fromActivity, Class targetActivity){
        Intent intent = new Intent(fromActivity,targetActivity);
        fromActivity.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(fromActivity).toBundle());
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void simpleStartForresultActivity(BaseActivity fromActivity, Class targetActivity, int requestCode){
        Intent intent = new Intent(fromActivity,targetActivity);
        intent.putExtra("transition","explode");
        fromActivity.startActivityForResult(intent,requestCode, ActivityOptions.makeSceneTransitionAnimation(fromActivity).toBundle());
    }
}
