package com.nexuslink.guidecar.util;

import android.content.Context;

import com.nexuslink.guidecar.GuideCarApplication;

import static com.nexuslink.guidecar.util.Config.THEME;
import static com.nexuslink.guidecar.util.Config.THEME_DEFAULT;
import static com.nexuslink.guidecar.util.Config.THEME_ID;
/**
 * Sp工具类
 * Created by Rye on 2017/5/10.
 */

public class SpUtil {

    public static Context context = GuideCarApplication.getContext();

    /**
     * 获得主题ID
     * @return 主题ID
     */

    public static int getThemeId(){
        return context.getSharedPreferences(THEME, Context.MODE_PRIVATE)
                .getInt(THEME_ID,THEME_DEFAULT);
    }

    /**
     * 存储/修改主题ID
     * @param themeId 保存或则修改的主题ID
     */

    public static void saveThemeId(int themeId){
        context.getSharedPreferences(THEME, Context.MODE_PRIVATE).edit()
                .putInt(THEME_ID,themeId)
                .apply();
    }
}
