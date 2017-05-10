package com.nexuslink.guidecar.util;

import android.content.Intent;

import com.nexuslink.guidecar.BaseActivity;

/**
 * intent的简单封装（不算封装吧）
 * Created by Rye on 2017/5/10.
 */

public class IntentUtil {
    public static void simpleStartActivity(BaseActivity fromActivity, Class targetActivity){
        Intent intent = new Intent(fromActivity,targetActivity);
        fromActivity.startActivity(intent);
    }
    public static void simpleStartForresultActivity(BaseActivity fromActivity, Class targetActivity,int requestCode){
        Intent intent = new Intent(fromActivity,targetActivity);
        fromActivity.startActivityForResult(intent,requestCode);
    }
}
