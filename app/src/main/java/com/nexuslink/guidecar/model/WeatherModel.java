package com.nexuslink.guidecar.model;

import com.nexuslink.guidecar.model.bean.ForecastBean;
import com.nexuslink.guidecar.net.RetrofitClient;
import com.nexuslink.guidecar.net.WeatherApiService;
import com.nexuslink.guidecar.util.Config;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rye on 2017/5/10.
 */

public class WeatherModel implements IWeatherModel{

    //请求天气
    @Override
    public void request(String city, final DataCallback<ForecastBean> callback){
        RetrofitClient.Create(WeatherApiService.class)
                .requestForecast("重庆", Config.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ForecastBean>() {
                    @Override
                    public void accept(@NonNull ForecastBean forecastBean) throws Exception {
                        callback.responseSuccess(forecastBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        callback.responseFailed(throwable.getMessage());
                    }
                });
    }
}
