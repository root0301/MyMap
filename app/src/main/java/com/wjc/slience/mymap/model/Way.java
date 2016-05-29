package com.wjc.slience.mymap.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 路线类
 */
public class Way implements Serializable{
    private int id;
    private String start_city;
    private String end_city;
    private float start_time;
    private float end_time;
    private int all_time;
    private float cost;
    private String vehicle;
    private String number;
    private Way next;
    private int week_day;
    private int end_id;

    public Way() {
    }

    public Way(String start_city, String end_city, float start_time, float end_time, int all_time, float cost, String vehicle, String number) {
        this.start_city = start_city;
        this.end_city = end_city;
        this.start_time = start_time;
        this.end_time = end_time;
        this.cost = cost;
        this.all_time = all_time;
        this.vehicle = vehicle;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_city() {
        return start_city;
    }

    public void setStart_city(String start_city) {
        this.start_city = start_city;
    }

    public String getEnd_city() {
        return end_city;
    }

    public void setEnd_city(String end_city) {
        this.end_city = end_city;
    }

    public float getStart_time() {
        return start_time;
    }

    public void setStart_time(float start_time) {
        this.start_time = start_time;
    }

    public float getEnd_time() {
        return end_time;
    }

    public void setEnd_time(float end_time) {
        this.end_time = end_time;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAll_time() {
        return all_time;
    }

    public void setAll_time(int all_time) {
        this.all_time = all_time;
    }

    public Way getNext() {
        return next;
    }

    public void setNext(Way next) {
        this.next = next;
    }

    public int getWeek_day() {
        return week_day;
    }

    public void setWeek_day(int week_day) {
        this.week_day = week_day;
    }

    public int getEnd_id() {
        return end_id;
    }

    public void setEnd_id(int end_id) {
        this.end_id = end_id;
    }

/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(start_city);
        dest.writeString(end_city);
        dest.writeString(vehicle);
        dest.writeString(number);
        dest.writeFloat(start_time);
        dest.writeFloat(end_time);
        dest.writeFloat(cost);
    }

    public static final Parcelable.Creator<Way> CREATOR = new Parcelable.ClassLoaderCreator<Way>() {

        @Override
        public Way createFromParcel(Parcel source) {
            return new Way();
        }

        @Override
        public Way[] newArray(int size) {
            return new Way[0];
        }

        @Override
        public Way createFromParcel(Parcel source, ClassLoader loader) {
            return null;
        }
    };
*/

}
