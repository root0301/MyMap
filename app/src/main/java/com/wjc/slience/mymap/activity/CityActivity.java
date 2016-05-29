package com.wjc.slience.mymap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.ActivityCollector;
import com.wjc.slience.mymap.common.CityAdapter;
import com.wjc.slience.mymap.db.MyMapDB;
import com.wjc.slience.mymap.model.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 选择城市
 */
public class CityActivity extends AppCompatActivity {

    private RecyclerView cityList;
    private CityAdapter adapter;
    private List<City> temp;
    private List<String> data;
    private int mType;
    private List<String> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        ActivityCollector.getInstance().addActivity(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("城市选择");
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mType = intent.getIntExtra("type",1);
        cities = new ArrayList<String>();
        initData();
        initRecyclerView(mType);

    }

    private void initData() {
        data = new ArrayList<String>();
        temp = new ArrayList<City>();
        temp = MyMapDB.getInstance(this).loadAllCity();
        for (int i=0;i<temp.size();i++) {
            String name = temp.get(i).getName();
            data.add(name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mType = intent.getIntExtra("type",1);
        initRecyclerView(mType);
    }

    private void initRecyclerView(int t) {
        cityList = (RecyclerView) findViewById(R.id.city_list);
        cityList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityAdapter(this,data,t);
        adapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            //判断是否应该出现复选框
            @Override
            public void onItemClick(View view, int position) {
                if (mType == 1 || mType == 2) {
                    Intent intent = new Intent(CityActivity.this, ChooseActivity.class);
                    intent.putExtra("name", data.get(position));
                    intent.putExtra("type", mType);
                    startActivity(intent);
                } else if (mType == 3){
                    if (cities.contains(data.get(position))) {
                        cities.remove(data.get(position));
                    } else {
                        cities.add(data.get(position));
                    }
                }
            }
        });

        cityList.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mType == 3) {
            getMenuInflater().inflate(R.menu.menu_city,menu);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.select) {
            Intent i = new Intent(CityActivity.this, ChooseActivity.class);
            i.putExtra("type",3);
            i.putStringArrayListExtra("cities", (ArrayList<String>) cities);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
