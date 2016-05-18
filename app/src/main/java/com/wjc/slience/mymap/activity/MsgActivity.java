package com.wjc.slience.mymap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.ActivityCollector;
import com.wjc.slience.mymap.common.LogUtil;

import org.w3c.dom.Text;

/**
 * Created by slience on 2016/5/18.
 */
public class MsgActivity extends Activity {

    TextView msg;
    String msgTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ActivityCollector.getInstance().addActivity(this);
        msg = (TextView) findViewById(R.id.msg);
        msgTxt = LogUtil.getInstance().readTheTrip();
        msg.setText(msgTxt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        msgTxt = LogUtil.getInstance().readTheTrip();
        msg.setText(msgTxt);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MsgActivity.this,ChooseActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
