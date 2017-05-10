package com.nexuslink.guidecar.util;

import android.widget.Toast;

import com.nexuslink.guidecar.GuideCarApplication;

/**
 * Toast简单封装
 * Created by Rye on 2017/5/10.
 */

public class ToastUtil {
    public static void shortToast(String str){
        Toast.makeText(GuideCarApplication.getContext(),str,Toast.LENGTH_SHORT).show();
    }
}
