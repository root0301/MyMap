package com.wjc.slience.mymap.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.ActivityCollector;
import com.wjc.slience.mymap.common.LogUtil;
import com.wjc.slience.mymap.common.Utility;
import com.wjc.slience.mymap.db.MyMapDB;
import com.wjc.slience.mymap.model.City;
import com.wjc.slience.mymap.model.Way;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static int currentTime = -1;
    public static int STRATEGY = 1;
    private int limited = 0;
    private static Boolean isQuit = false;
    private long mExitTime = 0;
    private List<Way> ways;
    private List<String> passedCityNames;
    private RadioButton R1;
    private RadioButton R2;
    private RadioButton R3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.getInstance().debug("choose is oncreate-------------------------");
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
        setCheck();
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
     *   设置起点终点的TextView
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
            for (int i=0;i<mSize;i++) {
                System.out.println("---"+passedCityNames.get(i));
            }
        } else if (nameType == 4) {
            startText = intent.getStringExtra("name");
            currentTime = intent.getIntExtra("time",-1);
        }

        switch (nameType) {
            case 1:
                startCity.setText(startText);
                if(endText != null) {
                    endCity.setText(endText);
                }
                break;
            case 2:
                endCity.setText(endText);
                if (startText != null) {
                    startCity.setText(startText);
                }
                break;
            case 3:
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
        LogUtil.getInstance().debug("choose is resume-------------------------");
        setCityText();
        setCheck();
    }

    /**
     *  事件的触发
     */
    @Override
    public void onClick(View v) {
        startCity = (TextView) findViewById(R.id.start_text);
        endCity = (TextView) findViewById(R.id.end_text);
        passedCity = (TextView) findViewById(R.id.passed_text);
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
                        String input = time.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(ChooseActivity.this,"请正确输入限时好么",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        Pattern p = Pattern.compile("[0-9]*");
                        Matcher m = p.matcher(input);
                        if (!m.matches()) {
                            Toast.makeText(ChooseActivity.this,"请正确输入限时好么",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        limited = Integer.valueOf(time.getText().toString().trim());
                    }

                        if (currentTime!=-1) {
                            wayIntent.putExtra("time",currentTime);
                        }
                        if (startCity.getText().toString().trim().equals("选择出发城市")||endCity.getText().toString().trim().equals("选择终点城市")) {
                            Toast.makeText(ChooseActivity.this,"请选择城市好么",Toast.LENGTH_SHORT).show();
                            break;
                        }
                System.out.println("the limiteTime is "+limited);
                System.out.println("the passed size is "+passedCityNames.size());
                System.out.println("the currentTime is "+currentTime);
                Utility test = new Utility(this);

                ways = test.findTheRoute(startCity.getText().toString().trim(), endCity.getText().toString().trim(),passedCityNames,limited ,STRATEGY,currentTime);

                        if (ways.size()==0) {
                            Toast.makeText(ChooseActivity.this,"没有符合条件的路线",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        wayIntent.putExtra("ways",(ArrayList)ways);
                        if (currentTime!=-1) {
                            wayIntent.putExtra("time",currentTime);
                        }
                        int[] received = new int[5];
                        received = test.calculateTimeMoney(ways,currentTime);
                        wayIntent.putExtra("allMoney",received[1]);
                        wayIntent.putExtra("allTime", received[2]);
                startActivity(wayIntent);

                       ///  finish();

                break;
            case R.id.history_show :
                Intent history = new Intent(ChooseActivity.this,MsgActivity.class);
                startActivity(history);
                break;
        }
    }


    public void setCheck() {
        R1 = (RadioButton) findViewById(R.id.choose_first);
        R2 = (RadioButton) findViewById(R.id.choose_second);
        R3 = (RadioButton) findViewById(R.id.choose_third);
        switch (STRATEGY) {
            case 1:
                R1.setChecked(true);
                break;
            case 2:
                R2.setChecked(true);
                break;
            case 3:
                R3.setChecked(true);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     *  退出操作
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

    public void test() {
        if (startText!=null) {
            startCity.setText(startText);
        }
        if (endText!=null) {
            endCity.setText(endText);
        }
        if (passedCityNames.size()!=0) {
            passedCity.setText("已选择"+passedCityNames.size()+"个城市");
        }
    }
}
