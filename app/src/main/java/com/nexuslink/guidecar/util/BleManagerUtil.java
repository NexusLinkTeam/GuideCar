package com.nexuslink.guidecar.util;

import com.clj.fastble.BleManager;
import com.nexuslink.guidecar.app.GuideCarApplication;

/**
 * Created by Rye on 2017/6/2.
 */

public class BleManagerUtil {
    private static BleManager instance;

    public static BleManager getInstance() {
        if(instance == null){
            return new BleManager(GuideCarApplication.getContext());
        }
        return instance;
    }
}
