package com.c1993.smartlive.activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocalDayWeatherForecast;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.LocationManagerProxy;
import com.c1993.smartlive.R;

import java.util.List;

public class WeatherActivity extends BaseActivity implements AMapLocalWeatherListener {

    private LocationManagerProxy mLocationManagerProxy;
    private TextView tvCity;
    private TextView tvTodayDate;
    private TextView tvToadyDayWeather;
    private TextView tvTodayTemp;
    private TextView tvTodayWind;
    private TextView tvTomorrowWeather;
    private TextView tvTomorrowTemp;
    private TextView tvTomorrowWind;
    private TextView tvAfterTomorrowWeather;
    private TextView tvAfterTomorrowTemp;
    private TextView tvAfterTomorrowWind;
    private TextView tvThreedayWeather;
    private TextView tvThreedayTemp;
    private TextView tvThreedayWind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        tvCity=(TextView)findViewById(R.id.tv_city);
        tvTodayDate=(TextView)findViewById(R.id.tv_today_date);
        tvToadyDayWeather=(TextView)findViewById(R.id.tv_today_day_weather);
        tvTodayTemp=(TextView)findViewById(R.id.tv_today_temp);
        tvTodayWind=(TextView)findViewById(R.id.tv_today_wind);
        tvTomorrowWeather=(TextView)findViewById(R.id.tv_tomorrow_weather);
        tvTomorrowTemp=(TextView)findViewById(R.id.tv_tomorrow_temp);
        tvTomorrowWind=(TextView)findViewById(R.id.tv_tomorrow_wind);
        tvAfterTomorrowWeather=(TextView)findViewById(R.id.tv_aftertomorrow_weather);
        tvAfterTomorrowTemp=(TextView)findViewById(R.id.tv_aftertomorrow_temp);
        tvAfterTomorrowWind=(TextView)findViewById(R.id.tv_aftertomorrow_wind);
        tvThreedayWeather=(TextView)findViewById(R.id.tv_threeday_weather);
        tvThreedayTemp=(TextView)findViewById(R.id.tv_threeday_temp);
        tvThreedayWind=(TextView)findViewById(R.id.tv_threeday_wind);

        init();
    }

    private void init(){
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestWeatherUpdates(
                LocationManagerProxy.WEATHER_TYPE_FORECAST, this);
    }

    @Override
    public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {
        if(aMapLocalWeatherForecast != null && aMapLocalWeatherForecast.getAMapException().getErrorCode() == 0){

            List<AMapLocalDayWeatherForecast> forcasts = aMapLocalWeatherForecast
                    .getWeatherForecast();
            for (int i = 0; i < forcasts.size(); i++) {
                AMapLocalDayWeatherForecast forcast = forcasts.get(i);
                switch (i) {
                    //今天天气
                    case 0:
                        tvCity.setText(forcast.getCity());
                        tvTodayDate.setText(forcast.getDate());
                        tvToadyDayWeather.setText(forcast.getDayWeather());
                        String todayTemp=forcast.getNightTemp()+"℃ ~ "+forcast.getDayTemp()+"℃";
                        String todayWind="风力:"+forcast.getDayWindPower()+"级";
                        tvTodayTemp.setText(todayTemp);
                        tvTodayWind.setText(todayWind);
                        break;
                    //明天天气
                    case 1:
                        tvTomorrowWeather.setText(forcast.getDayWeather());
                        String tomorrowTemp=forcast.getNightTemp()+"℃~"+forcast.getDayTemp()+"℃";
                        String tomorrowWind="风力:"+forcast.getDayWindPower()+"级";
                        tvTomorrowTemp.setText(tomorrowTemp);
                        tvTomorrowWind.setText(tomorrowWind);
                        break;
                    //后天天气
                    case 2:
                       tvAfterTomorrowWeather.setText(forcast.getDayWeather());
                        String aftertomorrowTemp=forcast.getNightTemp()+"℃~"+forcast.getDayTemp()+"℃";
                        String aftertomorrowWind="风力:"+forcast.getDayWindPower()+"级";
                        tvAfterTomorrowTemp.setText(aftertomorrowTemp);
                        tvAfterTomorrowWind.setText(aftertomorrowWind);
                        break;
                    //大后天天气
                    case 3:
                        tvThreedayWeather.setText(forcast.getDayWeather());
                        String threeTemp=forcast.getNightTemp()+"℃~"+forcast.getDayTemp()+"℃";
                        String threeWind="风力:"+forcast.getDayWindPower()+"级";
                        tvThreedayTemp.setText(threeTemp);
                        tvThreedayWind.setText(threeWind);
                        break;

                }
            }
        }else{
            // 获取天气预报失败
            Toast.makeText(this,"获取天气预报失败:"+ aMapLocalWeatherForecast.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onWeatherLiveSearched(AMapLocalWeatherLive arg0) {
        // TODO Auto-generated method stub

    }

}
