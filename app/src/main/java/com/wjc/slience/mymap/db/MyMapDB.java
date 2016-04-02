package com.wjc.slience.mymap.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wjc.slience.mymap.model.Way;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slience on 2016/4/2.
 */
public class MyMapDB {
    /**
     *  数据库名
     */
    public static final String DB_NAME = "my_map";

    /*
     *  数据库版本
     */
    public static final int VERSION = 1;

    private static MyMapDB myMapDB;

    private SQLiteDatabase db;

    /*
     *  构造方法私有化
     */
    private MyMapDB(Context context) {
        MyMapOpenHelper dbOpenHelper = new MyMapOpenHelper(context, DB_NAME, null, VERSION);
        db = dbOpenHelper.getWritableDatabase();
    }

    /*
     *  获取MyMap的实例
     */
    public synchronized static MyMapDB getInstance(Context context) {
        if(myMapDB == null) {
            myMapDB = new MyMapDB(context);
        }
        return myMapDB;
    }

    /*
     *  将路线数据存入数据库
     */
    public void saveWay(Way way) {
        if(way != null) {
            ContentValues values = new ContentValues();
            values.put("start_city",way.getStart_city());
            values.put("end_city",way.getEnd_city());
            values.put("start_time",way.getStart_time());
            values.put("end_time",way.getEnd_time());
            values.put("cost",way.getCost());
            values.put("vehicle",way.getVehicle());
            values.put("number",way.getNumber());
            db.insert("Route",null,values);
        }
    }

    public List<Way> loadWayByStartCity(String startCity) {
        List<Way> list = new ArrayList<Way>();
        Cursor cursor = db.query("Route",null,"start_city = ?",new String[]{String.valueOf(startCity)},null,null,null);
        if(cursor.moveToFirst()) {
            do {
                Way way = new Way();
                way.setId(cursor.getInt(cursor.getColumnIndex("id")));
                way.setStart_city(startCity);
                way.setEnd_city(cursor.getString(cursor.getColumnIndex("end_city")));
                way.setStart_time(cursor.getFloat(cursor.getColumnIndex("start_time")));
                way.setEnd_time(cursor.getFloat(cursor.getColumnIndex("end_time")));
                way.setCost(cursor.getFloat(cursor.getColumnIndex("cost")));
                way.setVehicle(cursor.getString(cursor.getColumnIndex("vehicle")));
                way.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                list.add(way);
            } while(cursor.moveToNext());
        }
        if(cursor != null) {
            cursor.close();
        }
        return list;
    }








}
