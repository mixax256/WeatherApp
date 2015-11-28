package com.example.user.weatherapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 18.11.2015.
 */
public class DataBase extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "Weather";
    public final static String DATE_COL = "date";
    public final static String TIME_COL = "time";
    public final static String CITY_COL = "city";
    public final static String COUNTRY_COL = "country";
    public final static String TEMP_COL = "temp";
    public final static String PRESSURE_COL = "pressure";
    public final static String WIND_SPEED_COL = "wind_speed";
    public final static String WIND_DEG_COL = "wind_deg";
    public final static String CURR_WEATHER_COL = "current_weather";
    public final static String ICO_COL = "icon";

    public DataBase(Context context) {
        super(context, "DBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" +
                " id integer primary key autoincrement, " +
                DATE_COL + " text, " +
                TIME_COL + " text, " +
                CITY_COL + " text, " +
                COUNTRY_COL + " text, " +
                TEMP_COL + " real, " +
                PRESSURE_COL + " real, " +
                WIND_SPEED_COL + " real, " +
                WIND_DEG_COL + " text, " +
                CURR_WEATHER_COL + " text, " +
                ICO_COL + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
