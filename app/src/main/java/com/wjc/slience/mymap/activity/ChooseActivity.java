package com.wjc.slience.mymap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.wjc.slience.mymap.R;

import java.util.List;

/**
 * Created by slience on 2016/4/2.
 */
public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{



    TextView startCity;
    TextView endCity;
    TextView passedCity;
    TextView history;
    RadioGroup rg;
    EditText time;
    FloatingActionButton searchButton;
    private static String startText = null;
    private static String endText = null;
    private static int mSize = 0;
    private int nameType;
    public static int STRATEGY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("路线");
        startCity = (TextView) findViewById(R.id.start_text);
        endCity = (TextView) findViewById(R.id.end_text);
        passedCity = (TextView) findViewById(R.id.passed_text);
        history = (TextView) findViewById(R.id.history_show);
        time = (EditText) findViewById(R.id.txt_time);
        rg = (RadioGroup) findViewById(R.id.rg);
        searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        startCity.setText("选择出发城市");
        endCity.setText("选择终点城市");
        passedCity.setText("若需要则选择途径城市");
        startCity.setOnClickListener(this);
        endCity.setOnClickListener(this);
        passedCity.setOnClickListener(this);
        history.setOnClickListener(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.choose_first:
                        STRATEGY = 1;
                        break;
                    case R.id.choose_second:
                        STRATEGY = 2;
                        break;
                    case R.id.choose_third:
                        STRATEGY = 3;
                        break;
                }
            }
        });
    }

    public void setCityText() {
        Intent intent = getIntent();
        nameType = intent.getIntExtra("type", -1);
        if(nameType == 1) {
            startText = intent.getStringExtra("name");
        } else if (nameType == 2) {
            endText = intent.getStringExtra("name");
        } else if (nameType == 3) {
            mSize = intent.getStringArrayListExtra("cities").size();
        }
        switch (nameType) {
            case 1:
                System.out.println("来自开始");
                startCity.setText(startText);
                if(endText != null) {
                    endCity.setText(endText);
                }
                break;
            case 2:
                System.out.println("来自终点");
                endCity.setText(endText);
                if (startText != null) {
                    startCity.setText(startText);
                }
                break;
            case 3:
                System.out.println("来自第三个");
                if(mSize != 0) {
                    passedCity.setText("已选择"+mSize+"个城市");
                }
                if (startText != null) {
                    startCity.setText(startText);
                }
                if(endText != null) {
                    endCity.setText(endText);
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCityText();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ChooseActivity.this, CityActivity.class);
        switch (v.getId()) {
            case R.id.start_text :
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.end_text:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.passed_text:
                intent.putExtra("type",3);
                startActivity(intent);
                break;
            case R.id.searchButton:
                //TODO:change to the map
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
