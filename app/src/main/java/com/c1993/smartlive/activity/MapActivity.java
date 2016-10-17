package com.c1993.smartlive.activity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.c1993.smartlive.R;

/**
 * Created by c1993 on 2016/5/9.
 */
public class MapActivity extends BaseActivity implements LocationSource,AMapLocationListener {

    private MapView mapView;
    private AMap aMap;
   private LocationManagerProxy mLocationManagerProxy;
    private OnLocationChangedListener mListener;

    private static final String TAG = "MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        mapView=(MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
        initLocation();
        setUpMap();

    }

    /**
     * 初始化Amap对象
     */
    private void init(){
        if(aMap==null){
            aMap=mapView.getMap();
        }
    }

    /**
     * 初始化定位
     */
    private void initLocation(){
        mLocationManagerProxy=LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork,-1,15,this);
        mLocationManagerProxy.setGpsEnable(false);

    }

    private void setUpMap(){
        aMap.setLocationSource(this);//设置监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 激活定位
     */
    public void activate(OnLocationChangedListener onLocationChangedListener){
        mListener=onLocationChangedListener;
        if(mLocationManagerProxy==null){
            mLocationManagerProxy=LocationManagerProxy.getInstance(this);
            mLocationManagerProxy.requestLocationData(
                    LocationProviderProxy.AMapNetwork,-1,10,this
            );
        }
    }

    /**
     * 停止定位
     */
    public void deactivate(){
        mListener=null;
        if(mLocationManagerProxy!=null){
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy=null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null&&aMapLocation.getAMapException().getErrorCode()==0){
            Double geoLat=aMapLocation.getLatitude();
            Double geolng=aMapLocation.getLongitude();
            Log.d(TAG,"Latitude="+geoLat.doubleValue()+",Longitude="+geolng.doubleValue());
            //通过AMapLocation.getExtras()方法获取位置描述信息
            String desc="";
            Bundle locBundle=aMapLocation.getExtras();
            if(locBundle!=null){
                desc=locBundle.getString("desc");
                Log.d("TAG","DESC="+desc);
            }
            mListener.onLocationChanged(aMapLocation);//显示系统小蓝点
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
