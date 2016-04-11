package com.wjc.slience.mymap.common;

import android.content.Context;

import com.wjc.slience.mymap.db.MyMapDB;
import com.wjc.slience.mymap.model.Way;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slience on 2016/4/2.
 */
public class Utility {

    private MyMapDB myMapDB;

    public Utility(Context context) {
        myMapDB = MyMapDB.getInstance(context);
    }

    /*
         * 计算两点最短路径，传入参数为起点和终点
         */
    public List<Way> findTheRoute(String startCity, String EndCity, int strategy) {

        List<Way> route = new ArrayList<Way>();
        List<Way> list = new ArrayList<Way>();
        list = myMapDB.loadWayByStartCity(startCity);

        return route;
    }



}
