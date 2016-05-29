package com.wjc.slience.mymap.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wjc.slience.mymap.model.City;
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
     *  @Description 构造方法私有化
     */
    private MyMapDB(Context context) {
        MyMapOpenHelper dbOpenHelper = new MyMapOpenHelper(context, DB_NAME, null, VERSION);
        db = dbOpenHelper.getWritableDatabase();
    }

    /*
     *  @Description 获取MyMap的实例
     */
    public synchronized static MyMapDB getInstance(Context context) {
        if(myMapDB == null) {
            myMapDB = new MyMapDB(context);
        }
        return myMapDB;
    }

    /*
     *  @param way要存入的路线
     *  @Description 将路线数据存入数据库
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
    /*
     *  @param city 要存入的城市
     *  @Description 将路线数据存入数据库
     */
    public void saveCity(City city) {
        if(city != null) {
            ContentValues values = new ContentValues();
            values.put("name",city.getName());
            db.insert("City",null,values);
        }
    }


    /*
     * @param startCity 传入的起始城市
     * @Description 通过起始城市获取到所有以该城市为起点的路线
     */
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
                way.setAll_time((int) cursor.getFloat(cursor.getColumnIndex("all_time")));
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

    /*
   * @param startCity 传入的起始城市
   * @Description 通过起始城市获取到所有以该城市为起点的路线
   */
    public List<Way> loadAllWay() {
        List<Way> list = new ArrayList<Way>();
        Cursor cursor = db.query("Route",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                Way way = new Way();
                way.setId(cursor.getInt(cursor.getColumnIndex("id")));
                way.setStart_city(cursor.getString(cursor.getColumnIndex("start_city")));
                way.setEnd_city(cursor.getString(cursor.getColumnIndex("end_city")));
                way.setStart_time(cursor.getFloat(cursor.getColumnIndex("start_time")));
                way.setEnd_time(cursor.getFloat(cursor.getColumnIndex("end_time")));
                way.setAll_time((int) cursor.getFloat(cursor.getColumnIndex("all_time")));
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


    /*
     * @Description从数据库中获取所有的城市
     */
    public List<City> loadAllCity() {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setName(cursor.getString(cursor.getColumnIndex("name")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    /*
     * @param wayID 路线ID
     * @Description 通过ID从数据库中获取相应的路线
     */
    public Way loadWayById(int wayID) {
        Way way = new Way();
        System.out.println("come from the database " +wayID);
        Cursor cursor = db.query("City",new String[]{"id"},"id = ?", new String[]{String.valueOf(wayID)},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                way.setId(cursor.getInt(cursor.getColumnIndex("id")));
                way.setStart_city(cursor.getString(cursor.getColumnIndex("start_city")));
                way.setEnd_city(cursor.getString(cursor.getColumnIndex("end_city")));
                way.setStart_time(cursor.getFloat(cursor.getColumnIndex("start_time")));
                way.setEnd_time(cursor.getFloat(cursor.getColumnIndex("end_time")));
                way.setAll_time((int) cursor.getFloat(cursor.getColumnIndex("all_time")));
                way.setCost(cursor.getFloat(cursor.getColumnIndex("cost")));
                way.setVehicle(cursor.getString(cursor.getColumnIndex("vehicle")));
                way.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            } while(cursor.moveToNext());
        }
        return way;
    }




}
