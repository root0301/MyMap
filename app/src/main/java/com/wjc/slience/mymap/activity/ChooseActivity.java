package com.wjc.slience.mymap.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.ActivityCollector;
import com.wjc.slience.mymap.common.Utility;
import com.wjc.slience.mymap.model.Way;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页，路线选择
 */
public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{



    TextView startCity;
    TextView endCity;
    TextView passedCity;
    TextView history;
    RadioGroup rg;
    EditText time;
    FloatingActionButton searchButton;
    Utility utility;
    private static String startText = null;
    private static String endText = null;
    private static int mSize = 0;
    private int nameType;
    private int currentTime = -1;
    public static int STRATEGY = 0;
    private int limited = 0;
    private static Boolean isQuit = false;
    private long mExitTime = 0;
    private List<Way> ways;
    private List<String> passedCityNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ActivityCollector.getInstance().addActivity(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("路线");
        startCity = (TextView) findViewById(R.id.start_text);
        endCity = (TextView) findViewById(R.id.end_text);
        passedCity = (TextView) findViewById(R.id.passed_text);
        history = (TextView) findViewById(R.id.history_show);
        time = (EditText) findViewById(R.id.txt_time);
        rg = (RadioGroup) findViewById(R.id.rg);
        utility = new Utility(this);
        ways = new ArrayList<Way>();
        passedCityNames = new ArrayList<String>();
        searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        startCity.setText("选择出发城市");
        endCity.setText("选择终点城市");
        passedCity.setText("若需要则选择途径城市");
        startCity.setOnClickListener(this);
        endCity.setOnClickListener(this);
        passedCity.setOnClickListener(this);
        history.setOnClickListener(this);
        searchButton.setOnClickListener(this);
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

    /**
     *  设置起点终点的TextView
     */
    public void setCityText() {
        Intent intent = getIntent();
        nameType = intent.getIntExtra("type", -1);
        if(nameType == 1) {
            startText = intent.getStringExtra("name");
        } else if (nameType == 2) {
            endText = intent.getStringExtra("name");
        } else if (nameType == 3) {
            passedCityNames = intent.getStringArrayListExtra("cities");
            mSize = passedCityNames.size();
        } else if (nameType == 4) {
            startText = intent.getStringExtra("name");
            currentTime = intent.getIntExtra("time",-1);
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
            case 4:
                startCity.setText(startText);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCityText();
    }

    /**
     * 事件的触发
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ChooseActivity.this, CityActivity.class);
        Intent wayIntent = new Intent(ChooseActivity.this,WaysActivity.class);
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
                if(STRATEGY == 3) {
                    limited = Integer.valueOf(time.getText().toString().trim());
                }
                //TODO: to add a progressbar here
                if (limited>=0 && limited<10000000) {
                    //ways = utility.findTheRoute(startCity.getText().toString().trim(), endCity.getText().toString().trim(),passedCityNames,limited ,STRATEGY);
                    //wayIntent.putExtra("ways",(ArrayList)ways);
                    wayIntent.putExtra("ways", (ArrayList) testWay());
                    if (currentTime!=-1) {
                        wayIntent.putExtra("time",currentTime);
                    }
                    startActivity(wayIntent);
                    finish();
                } else {
                    Toast.makeText(ChooseActivity.this,"Please input the right time limited",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.history_show :
                Intent history = new Intent(ChooseActivity.this,MsgActivity.class);
                startActivity(history);
                break;
        }
    }

    private List<Way> testWay() {
        List<Way> list = new ArrayList<Way>();
        for (int i=0;i<3;i++) {
            Way way = new Way();
            way.setId(i);
            switch (i) {
                case 0:
                    way.setStart_city("海口市");
                    way.setEnd_city("广州市");
                    way.setStart_time(20);
                    way.setEnd_time(22);
                    way.setAll_time(2);
                    way.setCost(300);
                    way.setVehicle("火车");
                    list.add(way);
                    break;
                case 1:
                    way.setStart_city("广州市");
                    way.setEnd_city("上海市");
                    way.setStart_time(22);
                    way.setEnd_time(1);
                    way.setAll_time(3);
                    way.setCost(500);
                    way.setVehicle("汽车");
                    list.add(way);
                    break;
                case 2:
                    way.setStart_city("上海市");
                    way.setEnd_city("北京市");
                    way.setStart_time(2);
                    way.setEnd_time(3);
                    way.setAll_time(1);
                    way.setCost(1200);
                    way.setVehicle("飞机");
                    list.add(way);
                    break;
            }
        }
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 退出操作
     */
      @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
          if ((System.currentTimeMillis() - mExitTime) > 2000) {//
              // 如果两次按键时间间隔大于2000毫秒，则不退出
              Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
             mExitTime = System.currentTimeMillis();// 更新mExitTime
          } else {
              ActivityCollector.getInstance().exit();// 否则退出程序
          }
          return true;
      }
      return super.onKeyDown(keyCode, event);

  }

}
