package com.wjc.slience.mymap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by slience on 2016/4/2.
 */
public class MyMapOpenHelper extends SQLiteOpenHelper {

    /**
        Plane表建表语句
     */
    public static final String CREATE_PLANE = "create table Plane ("
            + "id integer primary key autoincrement,"
            + "start_city text,"
            + "end_city text,"
            + "start_time real,"
            + "end_time real,"
            + "cost real,"
            + "number text)";

    /**
     Train表建表语句
     */
    public static final String CREATE_TRAIN = "create table Train ("
            + "id integer primary key autoincrement,"
            + "start_city text,"
            + "end_city text,"
            + "start_time real,"
            + "end_time real,"
            + "cost real,"
            + "number text)";

    /**
     Bus表建表语句
     */
    public static final String CREATE_BUS = "create table BUS ("
            + "id integer primary key autoincrement,"
            + "start_city text,"
            + "end_city text,"
            + "start_time real,"
            + "end_time real,"
            + "cost real,"
            + "number text)";

    public static final String CREATE_ROUTE = "create table route("
            + "id integer primary key autoincrement,"
            + "start_city text,"
            + "end_city text,"
            + "start_time real,"
            + "end_time real,"
            + "cost real,"
            + "vehicle text"
            + "number text)";

    public static final String CREATE_CITY = "create table city("
            + "id integer primary key autoincrement,"
            + "name text)";


    public MyMapOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
/*        db.execSQL(CREATE_PLANE);
        db.execSQL(CREATE_TRAIN);
        db.execSQL(CREATE_BUS);*/
        db.execSQL(CREATE_ROUTE);
        db.execSQL(CREATE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
