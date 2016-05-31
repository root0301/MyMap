package com.wjc.slience.mymap.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
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
 * 路线预览
 */
public class WaysActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<Way> list;
    private List<City> cities;
    private WayAdapter adapter;
    private FloatingActionButton search;
    private GeocodeSearch geocodeSearch;
    private ProgressDialog progressDialog;
    private static int COUNT = 0;
    private int currentTime = -1;
    private int allMoney = 0;
    private int allTime = 0;

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
        allMoney = intent.getIntExtra("allMoney",0);
        allTime = intent.getIntExtra("allTime",0);
        setCities();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("总时间:"+allTime+"小时 总费用:"+allMoney+"元");
        setSupportActionBar(toolbar);
        final Intent mapIntent = new Intent(WaysActivity.this, MapActivity.class);
        mapIntent.putExtra("ways", (ArrayList) list);
        init();
        //根据名称进行解析，得到经纬度
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
                //解析完成
                if (COUNT == cities.size()) {
                    closeProgressDialog();
                    mapIntent.putExtra("city", (ArrayList) cities);
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

    /**
     * 生产一个带有所有途径城市的list容器
     */
    private void setCities() {
        showProgressDialog();
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

    /**
     *  设置城市的经纬度
     */
    private void setLngAndLat() {
        for (int i =0; i<cities.size(); i++) {
            GeocodeQuery query = new GeocodeQuery(cities.get(i).getName(),cities.get(i).getName());
            geocodeSearch.getFromLocationNameAsyn(query);
        }
    }

    /**
     *  初始化列表
     */
    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.way_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WayAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    /**
     *  若生成了查询结果，则将结果存入文件中
     */
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

    public void showProgressDialog() {
        if (progressDialog==null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在联网解析地址");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    public void closeProgressDialog() {
        if (progressDialog!=null) {
            progressDialog.dismiss();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(WaysActivity.this,ChooseActivity.class);
            startActivity(intent);
            finish();
            COUNT = 0;
        }
        return super.onKeyDown(keyCode, event);
    }
}
