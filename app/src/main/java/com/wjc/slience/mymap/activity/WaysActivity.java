package com.wjc.slience.mymap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.WayAdapter;
import com.wjc.slience.mymap.model.Way;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slience on 2016/4/7.
 */
public class WaysActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Way> list = new ArrayList<Way>();
    WayAdapter adapter;
    FloatingActionButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        search = (FloatingActionButton) findViewById(R.id.began_travel);
        Intent intent = getIntent();
        list =  (ArrayList<Way>) getIntent().getSerializableExtra("ways");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("路线预览");
        setSupportActionBar(toolbar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: start the map activity
            }
        });
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.way_list);
        adapter = new WayAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
