package com.nexuslink.guidecar.net;

import com.nexuslink.guidecar.model.bean.ForecastBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * he_weather天气接口
 * Created by Rye on 2017/5/10.
 */

public interface WeatherApiService {

    //免费用户智能查找到包括当前日期的三天预报,所以后面两个只能捏造了
    @GET("/v5/forecast")
    Observable<ForecastBean> requestForecast(@Query("city") String city, @Query("key") String key);
}
