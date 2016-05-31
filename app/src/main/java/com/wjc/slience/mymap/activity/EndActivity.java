package com.wjc.slience.mymap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.common.ActivityCollector;

/**
 * Created by slience on 2016/5/31.
 */
public class EndActivity extends Activity {

    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        ActivityCollector.getInstance().addActivity(this);
        fb = (FloatingActionButton) findViewById(R.id.ufo);
        final Intent intent = new Intent(EndActivity.this,ChooseActivity.class);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
