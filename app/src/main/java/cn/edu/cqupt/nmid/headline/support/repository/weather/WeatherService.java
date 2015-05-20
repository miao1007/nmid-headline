package cn.edu.cqupt.nmid.headline.support.repository.weather;

import cn.edu.cqupt.nmid.headline.support.repository.weather.bean.Weather;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by leon on 12/4/14.
 */

@Deprecated public interface WeatherService {

  @GET("/api/android/weather") void getWeatherService(Callback<Weather> weatherCallback);
}
