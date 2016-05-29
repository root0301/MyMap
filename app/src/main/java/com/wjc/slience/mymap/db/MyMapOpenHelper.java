package com.wjc.slience.mymap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by slience on 2016/4/2.
 */
public class MyMapOpenHelper extends SQLiteOpenHelper {


    /*
     * @Description 路线时刻表建表语句
     */
    public static final String CREATE_ROUTE = "create table Route("
            + "id integer primary key autoincrement,"
            + "start_city text,"
            + "end_city text,"
            + "start_time real,"
            + "end_time real,"
            + "all_time real,"
            + "cost real,"
            + "vehicle text,"
            + "number text)";

    /*
     * @Description城市表建表语句
     */
    public static final String CREATE_CITY = "create table City("
            + "id integer primary key autoincrement,"
            + "name text)";


    public MyMapOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROUTE);
        db.execSQL(CREATE_CITY);
        addMsg(db);
        addCity(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void addMsg(SQLiteDatabase db){
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","南京市","08","11","3小时","95.5","火车","D2216"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","南京市","12","16","4小时","46.5","火车","K782"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","南京市","22","23","1小时","150","飞机","MU2882"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","南京市","13","14","1小时","139.5","火车","G7014"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","广州市","10","19","9小时","793","火车","G1301"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","广州市","14","11","21小时","428","汽车","SHZZ1430GZ"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","广州市","10","05","19小时","189.5","火车","T169"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","广州市","07","09","2小时","830","飞机","9C8835"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","广州市","14","16","2小时","1266","飞机","MU5311"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","武汉市","13","19","6小时","262","火车","D3006"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","武汉市","15","08","17小时","148.5","火车","K351"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","武汉市","18","05","11小时","316","汽车","7003412"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","武汉市","06","08","2小时","692","飞机","MF1845"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","长沙市","09","15","6小时","478","火车","G1347"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","长沙市","20","07","11小时","148.5","火车","Z247"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","长沙市","07","09","2小时","481","飞机","HO1123"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","杭州市","09","10","1小时","73","火车","G7349"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","杭州市","10","13","3小时","24.5","火车","K751"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","南宁市","10","21","11小时","767.5","火车","G1501"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","南宁市","10","18","32小时","240","火车","K1556"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","南宁市","06","09","3小时","471","飞机","　HQ1171"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","北京市","07","10","3小时","1040","飞机","CA1858"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","北京市","10","16","6小时","553","火车","G42"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","北京市","18","09","15小时","177.5","火车","T110"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","北京市","12","14","2小时","911","飞机","HU7608"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"上海市","北京市","18","20","2小时","994","飞机","KN5956"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","上海市","21","23","2小时","831","飞机","KN5987"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","上海市","16","21","5小时","553","火车","G19"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","上海市","14","20","6小时","553","火车","G141"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","上海市","19","10","15小时","177.5","火车","T109"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","上海市","11","08","21小时","156.5","火车","1461"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","长沙市","17","07","14小时","189.5","火车","Z201"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","长沙市","07","13","6小时","649","火车","G401"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","长沙市","22","14","16小时","189.5","火车","T289"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","长沙市","17","19","2小时","1317","飞机","HU7135"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","长沙市","06","09","3小时","875","飞机","MF8192"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","武汉市","11","22","11小时","152","火车","Z35"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","武汉市","14","19","5小时","520","火车","G519"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","武汉市","16","19","3小时","880","飞机","CZ6606"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","广州市","17","15","22小时","251","火车","Z201"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","广州市","10","18","8小时","862","火车","G71"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","广州市","20","06","10小时","709","火车","D901"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","广州市","08","11","3小时","1127","飞机","MU3011"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","广州市","10","14","4小时","1112","飞机","KN5819"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","广州市","12","10","22小时","251","火车","Z97"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","南京市","13","18","5小时","433.5","火车","G59"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","南京市","08","10","2小时","370","飞机","MU2802"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","昆明市","12","22","34小时","317","火车","Z161"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","昆明市","06","10","4小时","620","飞机","8L9988"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","大理市","06","10","4小时","2602","飞机","ZH1419"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","大理市","08","17","9小时","1925","飞机","HU7211"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","杭州市","13","09","20小时","300","汽车","3819"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","杭州市","23","20","21小时","189.5","火车","K101"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","杭州市","14","16","2小时","365","飞机","MU7324"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","拉萨市","20","12","40小时","360","火车","Z21"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","拉萨市","07","23","16小时","1720","飞机","MU2129"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","重庆市","15","16","25小时","229","火车","T9"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","重庆市","07","09","2小时","913","飞机","SC1437"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","成都市","18","21","3小时","802","飞机","KN5217"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"北京市","成都市","08","12","28小时","252","火车","K817"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","南宁市","08","12","4小时","169","火车","D3706"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","南宁市","09","21","12小时","105","火车","K365"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","北京市","14","17","3小时","845","飞机","KN5818"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","北京市","12","22","10小时","862","火车","G70"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","北京市","15","21","30小时","251","火车","K600"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","北京市","18","15","21小时","251","火车","Z98"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","上海市","06","09","3小时","460","飞机","9C8930"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","上海市","10","08","22小时","201","火车","K512"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","上海市","18","10","16小时","206","火车","Z100"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","武汉市","16","18","2小时","440","飞机","CA8232"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","武汉市","14","18","4小时","463.5","火车","G546"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","重庆市","09","11","2小时","524","飞机","PN6254"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","重庆市","16","18","2小时","841","飞机","SC4350"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","贵阳市","16","13","21小时","173.5","火车","K827"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","贵阳市","16","07","15小时","330","汽车","ZT033"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","成都市","11","08","21小时","340","汽车","BS13245336"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"广州市","成都市","11","22","35小时","240","火车","K587"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"成都市","广州市","07","09","2小时","776","飞机","EU2235"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"成都市","广州市","14","09","43小时","264","火车","K1223"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"成都市","北京市","08","10","2小时","788","飞机","ZH4113"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"成都市","北京市","11","10","23小时","254.5","火车","Z50"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"成都市","重庆市","13","14","1小时","154","火车","G8521"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"成都市","重庆市","09","14","5小时","98","汽车","400"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"成都市","拉萨市","14","09","43小时","329","火车","Z322"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"重庆市","拉萨市","11","14","3小时","900","飞机","CZ5313"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"重庆市","成都市","13","14","1小时","154","火车","G8522"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"重庆市","成都市","17","19","2小时","96.5","火车","D2201"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"重庆市","广州市","19","22","3小时","472","飞机","PN6205"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"重庆市","广州市","15","12","21小时","361","火车","K687"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"重庆市","北京市","12","14","2小时","765","飞机","SC1432"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"重庆市","北京市","12","10","22小时","251","?火车","Z96"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"拉萨市","北京市","11","17","6小时","2158","飞机","MU2334"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"拉萨市","北京市","15","08","41小时","360","火车","Z22"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"拉萨市","重庆市","18","13","43小时","335","火车","Z224"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"拉萨市","重庆市","10","13","3小时","749","飞机","PN6272"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","上海市","08","14","6小时","262","火车","D3012"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","上海市","14","15","1小时","480","飞机","MF1407"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","北京市","12","17","5小时","520.5","火车","G82"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","北京市","20","06","10小时","279.5","火车","Z38"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","广州市","14","15","1小时","415","飞机","ZH4949"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","广州市","15","19","4小时","463.5","火车","K435"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","杭州市","18","09","15小时","128.5","火车","K121"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"武汉市","杭州市","14","20","6小时","245","火车","D658"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","武汉市","11","21","10小时","225","汽车","BS14073411"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","武汉市","16","22","6小时","283","火车","G582"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","上海市","11","12","1小时","73","火车","G7508"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","上海市","11","13","2小时","24.5","火车","T82"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","北京市","20","22","2小时","435","飞机","MU2459"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","北京市","11","18","7小时","538.5","火车","G168"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","南京市","12","14","2小时","117.5","火车","G7616"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"杭州市","南京市","13","20","7小时","69","火车","K48"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","上海市","17","19","2小时","463","飞机","HO1126"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","上海市","13","19","6小时","478","火车","G1356"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","上海市","08","13","5小时","478","火车","G1346"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","北京市","16","06","14小时","189.5","火车","Z162"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","北京市","11","17","6小时","649","火车","G82"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","北京市","15","22","7小时","649","火车","G492"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","北京市","18","21","3小时","641","飞机","MF8147"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","南宁市","11","20","9小时","119","火车","Z285"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"长沙市","南宁市","14","20","6小时","289.5","火车","G1503"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南宁市","长沙市","09","15","6小时","289.5","火车","G530"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南宁市","长沙市","10","19","9小时","221","火车","Z6"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南宁市","广州市","13","17","4小时","169","火车","D2365"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南宁市","广州市","22","11","13小时","105","火车","K1206"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南宁市","上海市","11","13","26小时","243","火车","T82"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南宁市","上海市","21","06","33小时","240","火车","K1558"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南宁市","上海市","09","07","22小时","470","汽车","BS1327048"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南京市","上海市","08","10","2小时","134.5","火车","G7589"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南京市","上海市","15","17","2小时","95.5","火车","D3045"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南京市","上海市","13","17","4小时","95","汽车","BS15219480"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南京市","北京市","20","22","2小时","830","飞机","MU728"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南京市","北京市","11","16","5小时","443.5","火车","G42"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南京市","杭州市","12","13","1小时","117.5","火车","G581"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"南京市","杭州市","14","20","6小时","62.5","火车","K101"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"昆明市","北京市","11","15","4小时","572","飞机","KN5208"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"昆明市","北京市","21","06","33小时","317","火车","Z162"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"昆明市","大理市","09","15","6小时","64","火车","K9612"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"昆明市","大理市","16","20","4小时","110","汽车","C2958"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"大理市","昆明市","10","15","5小时","64","火车","K9608"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"大理市","昆明市","11","17","6小时","128","汽车","C2956"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"大理市","北京市","11","14","3小时","1050","飞机","MU5299"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"大理市","丽江市","15","17","2小时","34","火车","K9619"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"大理市","丽江市","13","15","2小时","75","汽车","C8306"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"丽江市","大理市","20","21","1小时","49","火车","K9618"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"丽江市","大理市","09","11","2小时","67","汽车","C0124"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"贵阳市","广州市","13","10","21小时","189.5","火车","K66"});
        db.execSQL("insert into Route(start_city,end_city,start_time,end_time,all_time,cost,vehicle,number) values(?,?,?,?,?,?,?,?)",new String[] {"贵阳市","广州市","14","18","4小时","267.5","火车","G2917"});
    }

    private void addCity(SQLiteDatabase db) {
        db.execSQL("insert into City(name) values(?)",new String[] {"上海市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"北京市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"广州市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"成都市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"重庆市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"拉萨市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"武汉市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"杭州市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"长沙市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"南宁市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"南京市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"昆明市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"大理市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"丽江市"});
        db.execSQL("insert into City(name) values(?)",new String[] {"贵阳市"});
    }
}
