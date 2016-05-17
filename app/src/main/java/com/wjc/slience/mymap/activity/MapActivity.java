package com.wjc.slience.mymap.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.model.City;
import com.wjc.slience.mymap.model.Way;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by slience on 2016/4/14.
 */
public class MapActivity extends AppCompatActivity {

    private AMap aMap;
    private MapView mapView;
    private List<Way> ways;
    private List<City> cities;
    private static int CURRENT_DATE = 1;
    private static int CURRENT_TIME = 0;
    private static int SECOND = 0;
    private static String NEXT_CITY =" ";
    private static int wayId = 0;
    private static int icon = 0;
    private static boolean running = false;
    private static double lonAdd = 0;
    private static double LatAdd = 0;
    private LatLng pos;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mapView = (MapView) findViewById(R.id.map);
        ways = new ArrayList<Way>();
        cities = new ArrayList<City>();
        mapView.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        CURRENT_TIME = calendar.get(Calendar.HOUR_OF_DAY);
        CURRENT_DATE = calendar.get(Calendar.DAY_OF_WEEK);
        initAMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        toolbar.setTitle("模拟运行");
        setSupportActionBar(toolbar);
        Intent waysIntent = getIntent();
        ways = (ArrayList<Way>)waysIntent.getSerializableExtra("ways");
        cities = (ArrayList<City>)waysIntent.getSerializableExtra("city");
        NEXT_CITY = cities.get(0).getName();
        pos = new LatLng(cities.get(0).getLat(),cities.get(0).getLng());
        icon = chooseTheIcon(0);
        addCityTags();
        timeChange();
    }

    private void timeChange() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SECOND = (SECOND + 1) % 10;
                updateTag();
                if (SECOND == 0) {
                    CURRENT_TIME = (CURRENT_TIME + 1) % 24;
                    if (CURRENT_TIME == 0) {
                        CURRENT_DATE = (CURRENT_DATE + 1) % 7;
                    }
                }
            }
        }, 0, 1000);
    }

    private void updateTag() {
       //TODO: update the tags
        System.out.println("the current time is " + CURRENT_TIME);
        if (CURRENT_TIME == ways.get(wayId).getStart_time()) {
            running = true;
        }

        if (running) {
            if (CURRENT_TIME==ways.get(wayId).getEnd_time()) {
                running = false;
                LatLng current = new LatLng(cities.get(wayId+1).getLat(),cities.get(wayId+1).getLng());
                pos = current;
                wayId++;
                icon = chooseTheIcon(wayId);
            } else calculateCurrentLocation();
            //addCityTags();
            CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
            //更新地图的显示区域
            aMap.moveCamera(cu);
            //清楚所有marker等覆盖物
            aMap.clear();
            MarkerOptions markerOptions = new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromResource(icon)).draggable(true);
            //添加marker
            aMap.addMarker(markerOptions);
            System.out.println("--------------------------------------");
        }
    }

    private void calculateCurrentLocation() {
        LatAdd = (cities.get(wayId+1).getLat() - cities.get(wayId).getLat())/(ways.get(wayId).getAll_time()*10);
        lonAdd = (cities.get(wayId+1).getLng() - cities.get(wayId).getLng())/(ways.get(wayId).getAll_time()*10);
        LatLng current = new LatLng(pos.latitude+LatAdd,pos.longitude+lonAdd);
        pos = current;
    }

    private void addCityTags() {
        PolylineOptions polys = new PolylineOptions();
        for (int  i=0;i<cities.size();i++) {
            MarkerOptions m = new MarkerOptions();
            LatLng latLng = new LatLng(cities.get(i).getLat(),cities.get(i).getLng());
            polys.add(latLng);
            m.position(latLng);
            m.title(cities.get(i).getName());
            m.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            if (i==0) {
                m.snippet("起点");
            }
            if(i==cities.size()-1) {
                m.title("终点");
                aMap.addPolyline(polys.geodesic(true).color(Color.RED));
            }
            aMap.addMarker(m);
        }
    }


    private void initAMap() {
        if(aMap == null) {
            aMap = mapView.getMap();
            CameraUpdate cu = CameraUpdateFactory.zoomTo(5);
            aMap.moveCamera(cu);
        }
    }

    private int chooseTheIcon(int i) {
        if (ways.get(i).getVehicle().equals("BUS")) {
            return R.drawable.bus;
        } else if (ways.get(i).getVehicle().equals("TRAIN")) {
            return R.drawable.train;
        } else if (ways.get(i).getVehicle().equals("PLANE")) {
            return R.drawable.plane;
        }
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

}
