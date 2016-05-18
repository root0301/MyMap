package com.wjc.slience.mymap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.ActivityCollector;
import com.wjc.slience.mymap.common.LogUtil;
import com.wjc.slience.mymap.common.WayAdapter;
import com.wjc.slience.mymap.model.City;
import com.wjc.slience.mymap.model.Way;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by slience on 2016/4/7.
 */
public class WaysActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Way> list;
    List<City> cities;
    WayAdapter adapter;
    FloatingActionButton search;
    GeocodeSearch geocodeSearch;
    private static int COUNT = 0;
    private int currentTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aitivity_ways);
        ActivityCollector.getInstance().addActivity(this);
        search = (FloatingActionButton) findViewById(R.id.began_travel);
        list = new ArrayList<Way>();
        cities = new ArrayList<City>();
        Intent intent = getIntent();
        list =  (ArrayList<Way>) getIntent().getSerializableExtra("ways");
        currentTime = intent.getIntExtra("time",-1);
        setCities();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("路线预览");
        setSupportActionBar(toolbar);
        final Intent mapIntent = new Intent(WaysActivity.this, MapActivity.class);
        mapIntent.putExtra("ways", (ArrayList) list);
        init();
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                GeocodeAddress addr = geocodeResult.getGeocodeAddressList().get(0);
                LatLonPoint latLonPoint = addr.getLatLonPoint();
                for (int j = 0; j < cities.size(); j++) {
                    if (addr.getCity().equals(cities.get(j).getName())) {
                        cities.get(j).setLat(latLonPoint.getLatitude());
                        cities.get(j).setLng(latLonPoint.getLongitude());
                        COUNT++;
                        break;
                    }
                }
                if (COUNT == cities.size()) {
                    mapIntent.putExtra("city", (ArrayList) cities);
                    System.out.println("解析完成----------------------------------");
                    Toast.makeText(WaysActivity.this, "解析完成", Toast.LENGTH_SHORT).show();
                    COUNT = 0;
                }
            }
        });
        setLngAndLat();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTime != -1) {
                    mapIntent.putExtra("time", currentTime);
                }
                startActivity(mapIntent);
            }
        });
        recordIntoFile();

    }


    private void setCities() {
        for (int i=0;i<list.size();i++) {
            City city = new City();
            city.setName(list.get(i).getStart_city());
            cities.add(city);
            if (i==list.size()-1) {
                City last = new City();
                last.setName(list.get(i).getEnd_city());
                cities.add(last);
            }
        }
    }

    private void setLngAndLat() {
        for (int i =0; i<cities.size(); i++) {
            GeocodeQuery query = new GeocodeQuery(cities.get(i).getName(),cities.get(i).getName());
            geocodeSearch.getFromLocationNameAsyn(query);
        }
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.way_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WayAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    private void recordIntoFile() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(now);
        //LogUtil.getInstance().debug(time + " 时查询结果如下:");
        LogUtil.getInstance().writeIntoFile(time + " 时查询结果如下:");
        for (int i=0; i<list.size();i++) {
            String info = new String(list.get(i).getStart_time()+"时从"+list.get(i).getStart_city()+"出发，历经"+list.get(i).getAll_time()
                    +"时于"+list.get(i).getEnd_time()+"时到达"+list.get(i).getEnd_city()+"，花费"+list.get(i).getCost()+"元");
           // LogUtil.getInstance().debug(info);
            LogUtil.getInstance().writeIntoFile(info);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

    }
}
