package cn.edu.cqupt.nmid.headline.support.api.weather;

import cn.edu.cqupt.nmid.headline.support.api.weather.bean.Weather;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by leon on 12/4/14.
 */
public interface WeatherService {

    @GET("/api/android/weather")
    void getWeatherService(Callback<Weather> weatherCallback);
}
