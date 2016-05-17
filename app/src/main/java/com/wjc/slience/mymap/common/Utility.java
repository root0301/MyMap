package com.wjc.slience.mymap.common;

import android.content.Context;

import com.wjc.slience.mymap.db.MyMapDB;
import com.wjc.slience.mymap.model.City;
import com.wjc.slience.mymap.model.Way;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slience on 2016/4/2.
 */
public class Utility {

    private MyMapDB myMapDB;
    private String startCity;
    private String endCity;
    private List<String> passedCity;
    private int limited;
    private static int STRATEGY;
    private List<Way> route;
    private List<City> allCity;

    public Utility(Context context) {
        myMapDB = MyMapDB.getInstance(context);
    }

    /*
     * 计算两点最短路径，传入参数为起点和终点
     */
    public List<Way> findTheRoute(String start, String end, List<String> passed, int li, int strategy) {
        route = new ArrayList<Way>();
        allCity = new ArrayList<City>();
        startCity = start;
        endCity = end;
        passedCity = passed;
        limited = li;
        allCity = myMapDB.loadAllCity();
        switch (strategy) {
            case 1:
                findTheTimeShortestRoute();
                break;
            case 2:
                findTheCostLeastRoute();
                break;
            case 3:
                findTheSuitableRoute();
                break;
        }

        return route;
    }

    private void findTheTimeShortestRoute() {
        List<String> allRoute = new ArrayList<String>();
        List<Way> temp = new ArrayList<Way>();
        int [][] wayRecord = new int[20][20];
        float [] costRecord = new float[20];
        float theShort = 0;
        String startNode = startCity;
        String nextNode = " ";
        int startId, endId;
        //TODO:初始化costRecord和wayRecord数组全为-1
        for (int k=0;k<costRecord.length;k++) {
            if (k!=cityNameId(startCity)) {
                costRecord[k] = 1000000;
            } else costRecord[k] = 0;
        }
        for(int r=0;r<wayRecord.length;r++)
            for (int c=0;c<wayRecord[0].length;c++) {
                wayRecord[r][c] = -1;
            }

        while(!(allRoute.contains(startCity)&&allRoute.contains(endCity))) {
            startNode = startCity;
            temp = myMapDB.loadWayByStartCity(startNode);
            startId = cityNameId(startNode);
            theShort = costRecord[startId];
            for (int i=0;i<temp.size();i++) {
                nextNode = temp.get(i).getEnd_city();
                endId = cityNameId(nextNode);
                float currentTime = theShort;
                while(currentTime >= 24){
                    currentTime -= 24;
                }
                float compare;
                if (temp.get(i).getEnd_time()-currentTime>=0) {
                    compare = temp.get(i).getEnd_time() - currentTime;
                } else compare = (temp.get(i).getEnd_time()-currentTime)%24;

                if (compare>=temp.get(i).getAll_time()) {
                    float add = 0;
                    if (temp.get(i).getEnd_time() > currentTime)
                        add = temp.get(i).getEnd_time() - currentTime;
                    else add = (temp.get(i).getEnd_time()-currentTime)%24;
                    if (add+theShort < costRecord[endId]) {
                        costRecord[endId] = add + theShort;
                     //TODO: 将wayRecord[startId]中的内容复制到wayRecord[endId],且将wayRecord[endId]的最后一个数修改成temp(i)对应的路线id
                    int last = 0;
                    for (int l=0;l<wayRecord[startId].length;l++) {
                        if (wayRecord[startId][l] >=0) {
                            wayRecord[endId][l] = wayRecord[startId][l];
                        } else {
                            last = l;
                            break;
                        }
                    }
                    wayRecord[endId][last] = temp.get(i).getId();                   }
                }
            }
            float min = 100000;
                int id = 0;
                for (int j=0; j<costRecord.length;j++) {
                    if(!(allRoute.contains(cityIdName(j)))) {
                        if(min > costRecord[j]) {
                            min = costRecord[j];
                            id = j;
                        }
                    }
                }
                 startNode = nextNode;
        }
        putIntoRoute(wayRecord[cityNameId(endCity)]);
    }

    private void findTheCostLeastRoute() {
        List<String> allRoute = new ArrayList<String>();
        List<Way> temp = new ArrayList<Way>();
        int [][] wayRecord = new int[20][20];
        float [] costRecord = new float[20];
        float theShort = 0;
        String startNode = startCity;
        String nextNode = " ";
        int startId, endId;
        //TODO:初始化costRecord和wayRecord数组全为-1
        for (int k=0;k<costRecord.length;k++) {
            if (k!=cityNameId(startCity)) {
                costRecord[k] = 1000000;
            } else costRecord[k] = 0;
        }
        for(int r=0;r<wayRecord.length;r++)
            for (int c=0;c<wayRecord[0].length;c++) {
                wayRecord[r][c] = -1;
            }
        while(!(allRoute.contains(startCity)&&allRoute.contains(endCity))) {
            temp = myMapDB.loadWayByStartCity(startNode);
            startId = cityNameId(startNode);
            theShort = costRecord[startId];
            for (int i=0; i<temp.size();i++) {
                nextNode = temp.get(i).getEnd_city();
                endId = cityNameId(nextNode);
                if(theShort+temp.get(i).getCost() < costRecord[endId]) {
                    costRecord[endId] = theShort + temp.get(i).getCost();
                    //TODO: 将wayRecord[startId]中的内容复制到wayRecord[endId],且将wayRecord[endId]的最后一个数修改成temp(i)对应的路线id
                    int last = 0;
                    for (int l=0;l<wayRecord[startId].length;l++) {
                        if (wayRecord[startId][l] >=0) {
                            wayRecord[endId][l] = wayRecord[startId][l];
                        } else {
                            last = l;
                            break;
                        }
                    }
                    wayRecord[endId][last] = temp.get(i).getId();
                }
                float min = 100000;
                int id = 0;
                for (int j=0; j<costRecord.length;j++) {
                    if(!(allRoute.contains(cityIdName(j)))) {
                        if(min > costRecord[j]) {
                            min = costRecord[j];
                            id = j;
                        }
                    }
                }
                 startNode = nextNode;
            }
        }
        putIntoRoute(wayRecord[cityNameId(endCity)]);
    }

    private void putIntoRoute(int[] list) {
        for (int i=0;list[i]>=0;i++) {
            route.add(myMapDB.loadWayById(list[i]));
        }
    }


    private String cityIdName(int id) {
        return allCity.get(id).getName();
    }

    private int cityNameId(String name) {
        int i;
        for (i=0;i<allCity.size();i++) {
            if (allCity.get(i).getName().equals(name))
                break;
        }
        return i;
    }



    private void findTheSuitableRoute() {
        List<String> allRoute = new ArrayList<String>();
        List<Way> temp = new ArrayList<Way>();
        int [][] wayRecord = new int[20][20];
        float [] costRecord = new float[20];
        float [] timeRecord = new float[20];
        float theShort = 0;
        float timeCost = 0;
        String startNode = startCity;
        String nextNode = " ";
        int startId, endId;
        boolean money = false, time = false;
        //TODO:初始化costRecord和wayRecord数组全为-1
        for (int k=0;k<costRecord.length;k++) {
            if (k!=cityNameId(startCity)) {
                costRecord[k] = 1000000;
                timeRecord[k] = 1000000;
            } else costRecord[k] = 0;
        }
        for(int r=0;r<wayRecord.length;r++)
            for (int c=0;c<wayRecord[0].length;c++) {
                wayRecord[r][c] = -1;
            }
        while(!(allRoute.contains(startCity)&&allRoute.contains(endCity))) {
            temp = myMapDB.loadWayByStartCity(startNode);
            startId = cityNameId(startNode);
            theShort = costRecord[startId];
            for (int i=0; i<temp.size();i++) {
                nextNode = temp.get(i).getEnd_city();
                endId = cityNameId(nextNode);
                if(theShort+temp.get(i).getCost() < costRecord[endId]) {
                    money = true;
                }

                float currentTime = theShort;
                while(currentTime >= 24){
                    currentTime -= 24;
                }
                float compare;
                if (temp.get(i).getEnd_time()-currentTime>=0) {
                    compare = temp.get(i).getEnd_time() - currentTime;
                } else compare = (temp.get(i).getEnd_time()-currentTime)%24;

                if (compare>=temp.get(i).getAll_time()) {
                    float add = 0;
                    if (temp.get(i).getEnd_time() > currentTime)
                        add = temp.get(i).getEnd_time() - currentTime;
                    else add = (temp.get(i).getEnd_time() - currentTime) % 24;

                    if (add + theShort < costRecord[endId]) {
                        time = true;
                    }
                }

                if (money && time) {
                    //TODO: 将wayRecord[startId]中的内容复制到wayRecord[endId],且将wayRecord[endId]的最后一个数修改成temp(i)对应的路线id
                    int last = 0;
                    for (int l=0;l<wayRecord[startId].length;l++) {
                        if (wayRecord[startId][l] >=0) {
                            wayRecord[endId][l] = wayRecord[startId][l];
                        } else {
                            last = l;
                            break;
                        }
                    }
                    wayRecord[endId][last] = temp.get(i).getId();
                }

                float min = 100000;
                int id = 0;
                for (int j=0; j<costRecord.length;j++) {
                    if(!(allRoute.contains(cityIdName(j)))) {
                        if(min > costRecord[j]) {
                            min = costRecord[j];
                            id = j;
                        }
                    }
                }
                startNode = nextNode;
            }
        }
        putIntoRoute(wayRecord[cityNameId(endCity)]);

    }


}

