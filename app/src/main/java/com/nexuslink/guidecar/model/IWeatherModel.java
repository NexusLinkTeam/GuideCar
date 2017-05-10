package com.nexuslink.guidecar.model;

import com.nexuslink.guidecar.model.bean.ForecastBean;

/**
 * Created by Rye on 2017/5/10.
 */

public interface IWeatherModel {
    void request(String city, DataCallback<ForecastBean> callback);
}
