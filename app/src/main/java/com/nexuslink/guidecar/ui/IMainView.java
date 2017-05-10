package com.nexuslink.guidecar.ui;

import com.nexuslink.guidecar.model.bean.ForecastBean;

import java.util.List;

/**
 * 主页与presenter通信接口
 * Created by Rye on 2017/5/10.
 */

public interface IMainView {
    void updateWeatherInfo(List<ForecastBean.HeWeather5Bean> weatherList);
}
