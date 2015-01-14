package cn.edu.cqupt.nmid.headline.support.api.weather;

import cn.edu.cqupt.nmid.headline.support.api.weather.bean.Weather;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by leon on 12/4/14.
 */
public interface WeatherService {

    //reference:
    // @link :http://127.0.0.1/txtt/public/api/android/weather

//    public static String API_BAIDU_ENDPOINT = "http://api.map.baidu.com";
//    public static String API_BAIDU_WEATHER_AK = "OsyKmFSMx2lVYyf5DA0EBmKX";
//    public static String API_BAIDU_WEATHER_LOCATION = "重庆";
//    public static String API_BAIDU_WEATHER_OUTPUT = "json";


    @GET("/txtt/public/api/android/weather")
    void getWeatherService(Callback<Weather> weatherCallback);
}
