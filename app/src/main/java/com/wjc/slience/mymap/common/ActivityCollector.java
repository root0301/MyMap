package com.wjc.slience.mymap.common;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * activity容器类
 */
public class ActivityCollector extends Application {

    //对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList实现了基于动态数组的数据结构，要移动数据。LinkedList基于链表的数据结构,便于增加删除
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ActivityCollector instance;


    private ActivityCollector(){ }

    //单例模式中获取唯一的MyApplication实例
    public static ActivityCollector getInstance() {
        if(null == instance) {
            instance = new ActivityCollector();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity)  {
        activityList.add(activity);
    }

    //遍历所有Activity并finish，调用此方法可退出程序
    public void exit(){
        for(Activity activity:activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
