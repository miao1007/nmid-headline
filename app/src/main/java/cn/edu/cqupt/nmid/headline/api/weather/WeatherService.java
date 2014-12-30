package cn.edu.cqupt.nmid.headline.api.weather;

import cn.edu.cqupt.nmid.headline.model.weather.Weather;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by leon on 12/4/14.
 */
public interface WeatherService {

    //reference:
    // @link :http://developer.baidu.com/map/index.php?title=car/api/weather

    public static String API_BAIDU_ENDPOINT = "http://api.map.baidu.com";
    public static String API_BAIDU_WEATHER_AK = "OsyKmFSMx2lVYyf5DA0EBmKX";
    public static String API_BAIDU_WEATHER_LOCATION = "重庆";
    public static String API_BAIDU_WEATHER_OUTPUT = "json";


    @GET("/telematics/v3/weather")
    void getWeatherService(@Query("ak") String ak,
                           @Query("location") String location,
                           @Query("output") String output,
                           Callback<Weather> weatherCallback);
}
