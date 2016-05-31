package com.wjc.slience.mymap.common;

import android.content.Context;

import com.wjc.slience.mymap.db.MyMapDB;
import com.wjc.slience.mymap.model.City;
import com.wjc.slience.mymap.model.Way;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * 路线计算类
 */
public class Utility {

    private MyMapDB myMapDB;
    private String startCity;
    private String endCity;
    private List<String> passedCity;
    private static int STRATEGY;
    private List<Way> route;
    List<Way> ways;
    private List<City> allCity;
    private Way adjCity[] = new Way[20];
    private String recordString[][] = new String[200][200];
    private int recordInt[][] = new int[200][200];

    private int limited;
    private int startState;
    private int endState;
    private int passedState;
    private int sTime;
    private int sDate;

    private int leastCost;
    private int leastTime;
    private static final int MAX = 100000000;
    private static final int MAX_CITY = 15;

    public Utility(Context context) {
        myMapDB = MyMapDB.getInstance(context);
    }


    /*
 *  @param start 起始城市
 *  @param end   终点城市
 *  @param passed 途径城市集合
 *  @param li 选择第三个策略时的限制
 *  @param strategy 选择的策略
 *  @Description 根据传入的参数计算最优路线
 */
    public List<Way> findTheRoute(String start, String end, List<String> passed, int li, int strategy,int cTime) {
        route = new ArrayList<Way>();
        allCity = new ArrayList<City>();
        startCity = start;
        endCity = end;
        passedCity = passed;
        STRATEGY = strategy;
        allCity = myMapDB.loadAllCity();
        startState = cityNameId(startCity);
        endState = cityNameId(endCity);
        passedState = 0;
        for (int i=0;i<passed.size();i++) {
            int c = cityNameId(passed.get(i));
            int state = 1<<c;
            passedState += state;
        }
        Calendar calendar = Calendar.getInstance();
        if (cTime==-1)
            sTime = calendar.get(Calendar.HOUR_OF_DAY);
        else
            sTime = cTime;
        limited = limited + li;
        sDate = calendar.get(Calendar.DAY_OF_WEEK);
        initGraph();

        switch (STRATEGY) {
            case 1:
                findTheTimeLeastRoute();
                break;
            case 2:
                findTheCostLeastRoute();
                break;
            case 3:
                findTheSuitRoute();
                break;
        }
        return route;
    }

    /**
     * @Description 在城市之间添加路线
     * @param id 路线的id
     * @param weekDay 路线的日期
     * @param startTime 路线开始的时间
     * @param startId 路线开始城市的id
     * @param arriveTime 路线到达的时间
     * @param nextId 路线到达的下一个城市的id
     * @param cost 路线的费用
     */
    public void addWay(int id, int weekDay, float startTime,int startId, float arriveTime,int nextId,float cost) {
        Way way = new Way();
        way.setId(id);
        way.setStart_time(startTime);
        way.setEnd_time(arriveTime);
        way.setCost(cost);
        way.setWeek_day(weekDay);
        way.setEnd_id(nextId);
        way.setNext(adjCity[startId]);
        way.setEnd_id(nextId);
        adjCity[startId] = way;

    }

    public void initGraph() {

        for (int i=0;i<adjCity.length;i++)              //初始化邻接表
            adjCity[i] = null;
        leastCost = MAX;
        leastTime = MAX;

        ways = new ArrayList<Way>();
        ways = myMapDB.loadAllWay();

        for (int i=0;i<ways.size();i++) {
            int id = ways.get(i).getId();
            String startCity = ways.get(i).getStart_city();
            String endCity = ways.get(i).getEnd_city();
            String vehicle = ways.get(i).getVehicle();
            String num = ways.get(i).getNumber();
            int startTime = (int)ways.get(i).getStart_time();
            int endTime = (int)ways.get(i).getEnd_time();
            int allTime = ways.get(i).getAll_time();
            int cost = (int)ways.get(i).getCost();
            //添加七天的路线
            for (int j=6;j>=0;j--) {
                int day = (sDate + j) % 7;
                if(j!=0 || startTime > sTime) {
                    addWay(id, day, j * 24 + (startTime - sTime), cityNameId(startCity),  j * 24 + (startTime - sTime) + allTime, cityNameId(endCity), cost);
                }
            }
        }
    }




    private boolean findTheTimeLeastRoute() {
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[MAX_CITY*100000+(1<<MAX_CITY)];
        int cost[][] = new int[MAX_CITY][1<<MAX_CITY];
        int time[][] = new int[MAX_CITY][1<<MAX_CITY];
        int referId[][] = new int[MAX_CITY][1<<MAX_CITY];
        int referCity[][] = new int[MAX_CITY][1<<MAX_CITY];

        for (int i=0;i<MAX_CITY;i++) {
            for (int j=0;j<(1<<MAX_CITY);j++) {
                cost[i][j] = -1;
                time[i][j] = 10000000;
                referId[i][j] = -1;
                referCity[i][j] = -1;
            }
        }

        cost[startState][1<<startState] = 0;
        time[startState][1<<startState] = 0;

        queue.add(startState*100000+(1<<startState));
        visited[startState*100000+(1<<startState)] = true;
        while(!queue.isEmpty()) {
            int temp = queue.poll();
            visited[temp] = false;
            int x = temp/100000;
            int state = temp%100000;
            Way p = new Way();
            p =adjCity[x];
            for (;p!=null;p=p.getNext()) {
                if( (((1<<p.getEnd_id())&state)^(1<<p.getEnd_id())) != 0 && //尚未访问过
                        time[x][state] <= p.getStart_time() &&   //时间允许
                        ( p.getEnd_time() < time[p.getEnd_id()][state+(1<<p.getEnd_id())] ||    //时间更低
                                ( p.getEnd_time() == time[p.getEnd_id()][state+(1<<p.getEnd_id())] &&//时间一样，费用更少的情况
                                        cost[x][state]+p.getCost() < cost[p.getEnd_id()][state+(1<<p.getEnd_id())]) )
                        ){
                    cost[p.getEnd_id()][state+(1<<p.getEnd_id())] = cost[x][state]+(int)p.getCost();                //更新费用
                    time[p.getEnd_id()][state+(1<<p.getEnd_id())] =(int)p.getEnd_time();                            //更新时间
                    referCity[p.getEnd_id()][state+(1<<p.getEnd_id())] = x;                                         //记录来自城市
                    referId[p.getEnd_id()][state+(1<<p.getEnd_id())] = p.getId();                                   //记录来自城市id

                    if(p.getEnd_id() == endState) continue;                                                        //到达目标城市，不需要更新

                    if(visited[p.getEnd_id()*100000+(state+(1<<p.getEnd_id()))] == false) {
                        //如果没有进入队列，则将这个状态放入队列
                        queue.add( p.getEnd_id()*100000+(state+(1<<p.getEnd_id())) );
                        visited[p.getEnd_id()*100000+(state+(1<<p.getEnd_id()))] = true;                                //进入队列标记为真
                    }
                }
            }
        }

        //从记录中取出最优路线
        int leastState = -1;
        for(int state=passedState;  state<(1<<MAX_CITY); state++)
            if( ((state&passedState)^passedState) == 0 &&                                                           //包含必须经过的城市
                    time[endState][state] < leastTime ) {
                leastState = state;
                leastTime = time[endState][state];
            }

        if(leastState == -1) {
            System.out.println("从这里退出---1");
            return false;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int tmpCity = endState;
        int tmpState = leastState;
        while(referCity[tmpCity][tmpState] != -1) {
            stack.push(referId[tmpCity][tmpState]);
            tmpState = tmpState - (1<<tmpCity);
            tmpCity = referCity[tmpCity][tmpState+(1<<tmpCity)];
        }

        if(tmpState != (1<<startState)) {
            System.out.println("从这里退出---2");
            return false;
        }

        while(!stack.isEmpty()) {
            int id = stack.pop();
            route.add(ways.get(id-1));
        }
        return true;
    }


//可能会陷入局部最优解
    private boolean findTheSuitRoute() {
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[MAX_CITY*100000+(1<<MAX_CITY)];
        int cost[][] = new int[MAX_CITY][1<<MAX_CITY];
        int time[][] = new int[MAX_CITY][1<<MAX_CITY];
        int referId[][] = new int[MAX_CITY][1<<MAX_CITY];
        int referCity[][] = new int[MAX_CITY][1<<MAX_CITY];

        for (int i=0;i<MAX_CITY;i++) {
            for (int j=0;j<(1<<MAX_CITY);j++) {
                cost[i][j] = 100000000;
                time[i][j] = -1;
                referId[i][j] = -1;
                referCity[i][j] = -1;
            }
        }

        cost[startState][1<<startState] = 0;
        time[startState][1<<startState] = 0;

        queue.add(startState*100000+(1<<startState));
        visited[startState*100000+(1<<startState)] = true;
        while(!queue.isEmpty()) {
            int temp = queue.poll();
            visited[temp] = false;
            int x = temp/100000;
            int state = temp%100000;
            Way p = new Way();
            p =adjCity[x];
            for (;p!=null;p=p.getNext()) {
                if( (((1<<p.getEnd_id())&state)^(1<<p.getEnd_id())) != 0 && //无访问过
                        p.getEnd_time() < limited &&
                        time[x][state] <= p.getStart_time() &&//时间允许
                        ( cost[x][state]+p.getCost() < cost[p.getEnd_id()][state+(1<<p.getEnd_id())] ||          //费用更低
                                ( cost[x][state]+p.getCost() == cost[p.getEnd_id()][state+(1<<p.getEnd_id())] && //费用一样的情况下，更早到达
                                        p.getEnd_time() < time[p.getEnd_id()][state+(1<<p.getEnd_id())]) )
                        ){
                    cost[p.getEnd_id()][state+(1<<p.getEnd_id())] = cost[x][state]+(int)p.getCost();            //更新费用
                    time[p.getEnd_id()][state+(1<<p.getEnd_id())] =(int)p.getEnd_time();                         //更新时间
                    referCity[p.getEnd_id()][state+(1<<p.getEnd_id())] = x;                                     //记录来自城市
                    referId[p.getEnd_id()][state+(1<<p.getEnd_id())] = p.getId();                               //记录来自城市id

                    if(p.getEnd_id() == endState) continue;     //到达目标城市，不用这个状态更新其他城市

                    if(visited[p.getEnd_id()*100000+(state+(1<<p.getEnd_id()))] == false) {
                        //如果没有进入队列，则将这个状态放入队列
                        queue.add( p.getEnd_id()*100000+(state+(1<<p.getEnd_id())) );
                        visited[p.getEnd_id()*100000+(state+(1<<p.getEnd_id()))] = true;                         //进入队列标记为真
                    }
                }
            }
        }

        //从记录中取出最优路线
        int leastState = -1;
        for(int state=passedState;  state<(1<<MAX_CITY); state++)
            if( ((state&passedState)^passedState) == 0 &&                                                   //包含必须经过的城市
                    cost[endState][state] < leastCost ) {
                leastState = state;
                leastCost = cost[endState][state];
            }

        if(leastState == -1) {
            return false;  //没有找到路线
        }

        Stack<Integer> stack = new Stack<Integer>();
        int tmpCity = endState;
        int tmpState = leastState;
        while(referCity[tmpCity][tmpState] != -1) {
            stack.push(referId[tmpCity][tmpState]);
            tmpState = tmpState - (1<<tmpCity);
            tmpCity = referCity[tmpCity][tmpState+(1<<tmpCity)];
        }

        if(tmpState != (1<<startState)) {
            return false;   //没有找到路线
        }

        while(!stack.isEmpty()) {
            int id = stack.pop();
            route.add(ways.get(id-1));
        }
        return true;
    }

    private boolean findTheCostLeastRoute() {
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[MAX_CITY*100000+(1<<MAX_CITY)];
        int cost[][] = new int[MAX_CITY][1<<MAX_CITY];
        int time[][] = new int[MAX_CITY][1<<MAX_CITY];
        int referId[][] = new int[MAX_CITY][1<<MAX_CITY];
        int referCity[][] = new int[MAX_CITY][1<<MAX_CITY];

        for (int i=0;i<MAX_CITY;i++) {
            for (int j=0;j<(1<<MAX_CITY);j++) {
                cost[i][j] = 100000000;
                time[i][j] = -1;
                referId[i][j] = -1;
                referCity[i][j] = -1;
            }
        }

        cost[startState][1<<startState] = 0;
        time[startState][1<<startState] = 0;

        queue.add(startState*100000+(1<<startState));
        visited[startState*100000+(1<<startState)] = true;
        while(!queue.isEmpty()) {
            int temp = queue.poll();
            visited[temp] = false;
            int x = temp/100000;
            int state = temp%100000;
            Way p = new Way();
            p =adjCity[x];
            for (;p!=null;p=p.getNext()) {
                if( (((1<<p.getEnd_id())&state)^(1<<p.getEnd_id())) != 0 && //无访问过
                        time[x][state] <= p.getStart_time() &&   //时间允许
                        ( cost[x][state]+p.getCost() < cost[p.getEnd_id()][state+(1<<p.getEnd_id())] ||          //费用更低
                                ( cost[x][state]+p.getCost() == cost[p.getEnd_id()][state+(1<<p.getEnd_id())] && //费用一样的情况下，更早到达
                                        p.getEnd_time() < time[p.getEnd_id()][state+(1<<p.getEnd_id())]) )
                        ){
                    cost[p.getEnd_id()][state+(1<<p.getEnd_id())] = cost[x][state]+(int)p.getCost();            //更新费用
                    time[p.getEnd_id()][state+(1<<p.getEnd_id())] =(int)p.getEnd_time();                         //更新时间
                    referCity[p.getEnd_id()][state+(1<<p.getEnd_id())] = x;                                     //记录来自城市
                    referId[p.getEnd_id()][state+(1<<p.getEnd_id())] = p.getId();                               //记录来自城市id

                    if(p.getEnd_id() == endState) continue;     //到达目标城市，不用这个状态更新其他城市

                    if(visited[p.getEnd_id()*100000+(state+(1<<p.getEnd_id()))] == false) {
                        //如果没有进入队列，则将这个状态放入队列
                        queue.add( p.getEnd_id()*100000+(state+(1<<p.getEnd_id())) );
                        visited[p.getEnd_id()*100000+(state+(1<<p.getEnd_id()))] = true;                         //进入队列标记为真
                    }
                }
            }
        }

        //从记录中取出最优路线
        int leastState = -1;
        for(int state=passedState;  state<(1<<MAX_CITY); state++)
            if( ((state&passedState)^passedState) == 0 &&                                                   //包含必须经过的城市
                    cost[endState][state] < leastCost ) {
                leastState = state;
                leastCost = cost[endState][state];
            }

        if(leastState == -1) {
            return false;  //没有找到路线
        }

        Stack<Integer> stack = new Stack<Integer>();
        int tmpCity = endState;
        int tmpState = leastState;
        while(referCity[tmpCity][tmpState] != -1) {
            stack.push(referId[tmpCity][tmpState]);
            tmpState = tmpState - (1<<tmpCity);
            tmpCity = referCity[tmpCity][tmpState+(1<<tmpCity)];
        }

        if(tmpState != (1<<startState)) {
            return false;   //没有找到路线
        }

        while(!stack.isEmpty()) {
            int id = stack.pop();
            route.add(ways.get(id-1));
        }
        return true;
    }


    public int[] calculateTimeMoney(List<Way> list, int sTime) {
        int money=0, time=0;
        //计算总费用
        for (int i=0;i<list.size();i++) {
            money += list.get(i).getCost();
        }
        int now = 0;
        Calendar calendar = Calendar.getInstance();
        if (sTime==-1)
            now = calendar.get(Calendar.HOUR_OF_DAY);
        else
            now = sTime;
        for (int i=0;i<list.size();i++) {
            int temp = (int)list.get(i).getStart_time() - now;
            time = temp + list.get(i).getAll_time() + time;
            now = (int)list.get(i).getEnd_time();
        }
        int[] result = new int[5];
        result[1] = money;
        result[2] = time;

        return result;
    }






    /*
    *  @param id 城市id
    *  @Description 通过城市id获取城市
    */
    private String cityIdName(int id) {
        return allCity.get(id).getName();
    }

    /*
     *  @param name 城市名
     *  @Description 通过城市名获取城市id
     */
    private int cityNameId(String name) {
        int i;
        for (i=0;i<allCity.size();i++) {
            if (allCity.get(i).getName().equals(name))
                break;
        }
        return i;
    }
}
