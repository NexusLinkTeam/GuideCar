package com.nexuslink.guidecar.presenter;

import com.nexuslink.guidecar.model.IWeatherModel;
import com.nexuslink.guidecar.model.DataCallback;
import com.nexuslink.guidecar.model.WeatherModel;
import com.nexuslink.guidecar.model.bean.ForecastBean;
import com.nexuslink.guidecar.ui.IMainView;
import com.nexuslink.guidecar.util.Config;
import com.nexuslink.guidecar.util.ToastUtil;

import java.util.List;

/**
 * 主页presenter
 * Created by Rye on 2017/5/10.
 */

public class MainPresenter implements DataCallback<ForecastBean>,IMainPresenter{

    private IMainView view;
    private IWeatherModel weatherModel;

    public MainPresenter(IMainView view) {
        this.view = view;
        weatherModel = new WeatherModel();
    }

    //天气请求成功的回调
    @Override
    public void responseSuccess(ForecastBean bean) {
        List<ForecastBean.HeWeather5Bean> weatherList = bean.getHeWeather5();
        view.updateWeatherInfo(weatherList);
    }

    //天气请求失败的回调
    @Override
    public void responseFailed(String exception) {
        ToastUtil.shortToast(exception);
    }

    @Override
    public void requestWeather(String city) {
        weatherModel.request(city,this);
    }
}
