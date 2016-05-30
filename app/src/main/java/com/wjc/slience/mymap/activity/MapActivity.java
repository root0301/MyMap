package com.wjc.slience.mymap.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;

import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.ActivityCollector;
import com.wjc.slience.mymap.model.City;
import com.wjc.slience.mymap.model.Way;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 运行界面
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
    private int currentTime = -1;
    private static boolean running = false;
    private static double lonAdd = 0;
    private static double LatAdd = 0;
    private LatLng pos;
    private TextView timeMessage;
    private TextView wayMessage;
    private FloatingActionButton stop;
    private int flag = 100;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_map);
        ActivityCollector.getInstance().addActivity(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        timeMessage = (TextView) findViewById(R.id.time_message);
        wayMessage = (TextView) findViewById(R.id.way_message);
        mapView = (MapView) findViewById(R.id.map);
        stop = (FloatingActionButton) findViewById(R.id.stop);
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
        currentTime = waysIntent.getIntExtra("time", -1);
        if (currentTime!=-1) {
            //设置系统时间
            CURRENT_TIME = currentTime;
        }
        timeMessage.setText("当前时间:" + CURRENT_TIME + ":00");
/*        wayMessage.setText(ways.get(wayId).getStart_time()+"时从"+ways.get(wayId).getStart_city()+"出发，历经"+ways.get(wayId).getAll_time()
                +"时于"+ways.get(wayId).getEnd_time()+"时到达"+ways.get(wayId).getEnd_city());*/
        //设置marker的初始值
        wayId = 0;
        pos = new LatLng(cities.get(0).getLat(),cities.get(0).getLng());
        icon = chooseTheIcon(0);
        addCityTags();
        timeChange();
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

    }

    /**
     * 中途修改路线弹出的对话框
     */
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改路线");
        builder.setMessage("确认修改？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (running==false) {
                    //处于停止状态，可直接进行路线选择
                    timer.cancel();
                    Intent intent = new Intent(MapActivity.this,ChooseActivity.class);
                    intent.putExtra("type",4);
                    intent.putExtra("time",CURRENT_TIME);
                    intent.putExtra("name", ways.get(wayId).getStart_city());
                    System.out.println("传出去的参数是"+CURRENT_TIME);
                    startActivity(intent);
                    finish();
                } else {
                    //处在行程中，需要等到到达下一个城市后，才可进行路线选择
                    Toast.makeText(MapActivity.this,"在路途中尚不可变更，到达下一城市将为你重新选择路线",Toast.LENGTH_SHORT).show();
                    flag = (int) ways.get(wayId).getEnd_time();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 计时器，每过1秒执行一次，执行十次后，CURRENT_TIME推进到下一小时
     */
    private void timeChange() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SECOND = (SECOND + 1) % 10;
                updateTag();
                if (SECOND == 0) {
                    CURRENT_TIME = (CURRENT_TIME + 1) % 24;
                    //子线程不能更新UI，调用 runOnUiThread 进行UI的更新
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timeMessage.setText("当前时间:" + CURRENT_TIME );
                        }
                    });
                    //到达0点，天数加一
                    if (CURRENT_TIME == 0) {
                        CURRENT_DATE = (CURRENT_DATE + 1) % 7;
                    }
                }
            }
        }, 0, 1000);
    }


    /**
     *  根据时间的推进，更新用户所处的位置
     */
    private void updateTag() {
        //到达终点，旅行结束，返回首页
        if (wayId == ways.size()-1 && CURRENT_TIME == ways.get(wayId).getEnd_time()) {
            timer.cancel();
            Intent intent = new Intent(MapActivity.this,ChooseActivity.class);
            startActivity(intent);
            finish();
        }

        //用户点击了更改路线的按钮，但处于行程中，一旦到达下一城市，就跳转到路线选择界面
        if (CURRENT_TIME == flag) {
            timer.cancel();
            Intent intent = new Intent(MapActivity.this,ChooseActivity.class);
            intent.putExtra("type",4);
            intent.putExtra("time",CURRENT_TIME);
            intent.putExtra("name", ways.get(wayId).getEnd_city());
            startActivity(intent);
            finish();
        }
        //当前时间等于路线的开始时间，running状态设置为true
        if (CURRENT_TIME == ways.get(wayId).getStart_time()) {
            running = true;
        }

        //如果处于运行状态，设置pos的相关属性
        if (running) {
            //当前时间为到达时间，运行状态设置为false，设置用户的位置为路线的到达城市；
            //否则计算用户的位置
            if (CURRENT_TIME==ways.get(wayId).getEnd_time()) {
                running = false;
                LatLng current = new LatLng(cities.get(wayId+1).getLat(),cities.get(wayId+1).getLng());
                pos = current;
                wayId++;
                if (wayId<=ways.size()-1) {
                    icon = chooseTheIcon(wayId);
                }
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
    }
}

    /**
     *  计算用户的位置； 根据初始城市和要达到城市的经纬度，计算速度 LatAdd 和 LonAdd ，同时更新pos的位置
     */
    private void calculateCurrentLocation() {
        LatAdd = (cities.get(wayId+1).getLat() - cities.get(wayId).getLat())/(ways.get(wayId).getAll_time()*10);
        lonAdd = (cities.get(wayId+1).getLng() - cities.get(wayId).getLng())/(ways.get(wayId).getAll_time()*10);
        LatLng current = new LatLng(pos.latitude+LatAdd,pos.longitude+lonAdd);
        pos = current;
    }

    //在地图上绘制出用户的路线
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

    /**
     * 初始化地图
     */
    private void initAMap() {
        if(aMap == null) {
            aMap = mapView.getMap();
            CameraUpdate cu = CameraUpdateFactory.zoomTo(5);
            aMap.moveCamera(cu);
        }
    }

    /**
     *  判断使用的那种交通工具
     */
    private int chooseTheIcon(int i) {
        if (ways.get(i).getVehicle().equals("汽车")) {
            return R.drawable.bus;
        } else if (ways.get(i).getVehicle().equals("火车")) {
            return R.drawable.train;
        } else if (ways.get(i).getVehicle().equals("飞机")) {
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

    /**
     *  点击返回键时，弹出是否改变路线的选择
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog();
        }
        return super.onKeyDown(keyCode, event);
    }
}
