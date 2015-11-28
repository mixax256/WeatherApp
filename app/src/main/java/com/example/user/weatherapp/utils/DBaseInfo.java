package com.example.user.weatherapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.user.weatherapp.R;
import com.example.user.weatherapp.model.DataBase;

import org.json.JSONObject;

/**
 * Created by user on 18.11.2015.
 */
public class DBaseInfo {
    final static String LOG_TAG = "DBase";

    private SQLiteDatabase db;
    private Context context;
    boolean isEmpty;

    public DBaseInfo(JSONObject jsonObject, Context cont) {
        context = cont;
        ContentValues cv = new ContentValues();
        ParseWeatherInfo wInf = new ParseWeatherInfo(jsonObject);
        boolean jsonObjNull = wInf.isNull();
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query(DataBase.TABLE_NAME, null, null, null, null, null, null);

        if (jsonObjNull == false) {
            int count = wInf.getListSize();

            if (c.getCount() == 0)
                isEmpty = true;

            for (int i = 0; i < count; i++) {
                wInf.setNumberInArr(i);
                cv.put(DataBase.DATE_COL, wInf.getDate());
                cv.put(DataBase.TIME_COL, wInf.getTime());
                cv.put(DataBase.CITY_COL, wInf.getCity());
                cv.put(DataBase.COUNTRY_COL, wInf.getCountry());
                cv.put(DataBase.TEMP_COL, wInf.getTemp());
                cv.put(DataBase.PRESSURE_COL, wInf.getPressure());
                cv.put(DataBase.WIND_SPEED_COL, wInf.getWindSpeed());
                cv.put(DataBase.WIND_DEG_COL, wInf.getWindDegree());
                cv.put(DataBase.CURR_WEATHER_COL, wInf.getCurrentWeather());
                cv.put(DataBase.ICO_COL, wInf.getIconURL());
                if (isEmpty == true)
                    db.insert(DataBase.TABLE_NAME, null, cv);
                else
                    db.update(DataBase.TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(i + 1)});
            }
        }
        c.close();
        dbHelper.close();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public double getCurrTemp() {
        double temp;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        temp = c.getDouble(c.getColumnIndex(DataBase.TEMP_COL));

        c.close();
        dbHelper.close();
        return temp;
    }

    public double getCurrPressure() {
        double pressure;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        pressure = c.getDouble(c.getColumnIndex(DataBase.PRESSURE_COL));

        c.close();
        dbHelper.close();
        return pressure;
    }

    public String getCity() {
        String city;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        city = c.getString(c.getColumnIndex(DataBase.CITY_COL));

        c.close();
        dbHelper.close();
        return city;
    }

    public String getCountry() {
        String country;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        country = c.getString(c.getColumnIndex(DataBase.COUNTRY_COL));

        c.close();
        dbHelper.close();
        return country;
    }

    public double getWindSpeed() {
        double windSpeed;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        windSpeed = c.getDouble(c.getColumnIndex(DataBase.WIND_SPEED_COL));

        c.close();
        dbHelper.close();
        return windSpeed;
    }

    public String getWindDegree() {
        String windDegree;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        windDegree = c.getString(c.getColumnIndex(DataBase.WIND_DEG_COL));

        c.close();
        dbHelper.close();
        return windDegree;
    }

    public String getCurrentWeather() {
        String currentWeather;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        currentWeather = c.getString(c.getColumnIndex(DataBase.CURR_WEATHER_COL));

        c.close();
        dbHelper.close();
        return currentWeather;
    }

    public int getIconID() {
        int iconID;
        String[] selectionArgs = new String[] {String.valueOf(1)};
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, null, "id = ?", selectionArgs, null, null, null);
        c.moveToFirst();

        String icID;
        icID = c.getString(c.getColumnIndex(DataBase.ICO_COL));

        c.close();
        dbHelper.close();
        iconID = getIcID(icID);
        return iconID;
    }

    private int getIcID(String icID) {
        if (icID.equals("01d"))
            return  R.drawable._01d;
        else if (icID.equals("01n"))
            return R.drawable._01n;
        else if (icID.equals("02d"))
            return R.drawable._02d;
        else if (icID.equals("02n"))
            return R.drawable._02n;
        else if (icID.equals("03d"))
            return R.drawable._03d;
        else if (icID.equals("03n"))
            return R.drawable._03n;
        else if (icID.equals("04d"))
            return R.drawable._04d;
        else if (icID.equals("04n"))
            return R.drawable._04n;
        else if (icID.equals("09d"))
            return R.drawable._09d;
        else if (icID.equals("09n"))
            return R.drawable._09n;
        else if (icID.equals("10d"))
            return R.drawable._10d;
        else if (icID.equals("10n"))
            return R.drawable._10n;
        else if (icID.equals("11d"))
            return R.drawable._11d;
        else if (icID.equals("11n"))
            return R.drawable._11n;
        else if (icID.equals("13d"))
            return R.drawable._13d;
        else if (icID.equals("13n"))
            return R.drawable._13n;
        else if (icID.equals("50d"))
            return R.drawable._50d;
        else return R.drawable._50n;
    }

    public String getMinMaxTemp(String date, boolean inCelsium) {
        String minMaxTemp;
        String[] columns = new String[] { DataBase.TEMP_COL };
        String selection = DataBase.DATE_COL + " = ? and (" + DataBase.TIME_COL + " = ? or " +
                DataBase.TIME_COL + " = ?)";
        String[] selectionArgs = new String[] { date, "00:00:00", "15:00:00" };
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        c.moveToFirst();
        double minTemp = Math.round(c.getDouble(c.getColumnIndex(DataBase.TEMP_COL)));
        c.moveToLast();
        double maxTemp = Math.round(c.getDouble(c.getColumnIndex(DataBase.TEMP_COL)));

        if (inCelsium == false) {
            minTemp = toFahrenheit(minTemp);
            maxTemp = toFahrenheit(maxTemp);
        }
            minMaxTemp = Math.round(minTemp) + "° - " + Math.round(maxTemp) + "°";


        c.close();
        dbHelper.close();
        return minMaxTemp;
    }

    private double toFahrenheit(double temp) {
        return Math.round((temp - 32) * 5 / 9);
    }

    public int getIconAtDate(String date) {
        String icon = "";
        String[] columns = new String[] { DataBase.ICO_COL };
        String selection = DataBase.DATE_COL + " = ? and " + DataBase.TIME_COL + " = ?";
        String[] selectionArgs = new String[] { date, "15:00:00" };
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query(DataBase.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        c.moveToFirst();

        icon = c.getString(c.getColumnIndex(DataBase.ICO_COL));

        c.close();
        dbHelper.close();
        return getIcID(icon);
    }

    public String[] getDates() {
        String[] dates = new String[4];
        DataBase dbHelper = new DataBase(context);
        db = dbHelper.getWritableDatabase();

        String query = "SELECT DISTINCT " + DataBase.DATE_COL + " FROM " + DataBase.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            int i = 0;
            c.moveToNext();
            do {
                dates[i] = c.getString(c.getColumnIndex(DataBase.DATE_COL));
                i++;
            } while (c.moveToNext() && i < dates.length);
        }

        c.close();
        dbHelper.close();
        return dates;
    }

    private void showDB(Cursor c) {
        if (c.moveToFirst()) {
            String str;
            do {
                str = "";
                for (String cn : c.getColumnNames()) {
                    str = str.concat(cn + " = "
                            + c.getString(c.getColumnIndex(cn)) + "; ");
                }
                Log.d(LOG_TAG, str);

            } while (c.moveToNext());
        }
    }
}
