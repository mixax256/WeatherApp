package com.example.user.weatherapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 05.11.2015.
 */
public class ParseWeatherInfo {
    private JSONObject jsonObj = null;
    private JSONArray jsonArray = null;
    private int numberInArray = 0;
    private JSONObject jsonDay = null;

    static double toMmHg = 0.75006375541921;

    public ParseWeatherInfo(JSONObject wInf) {
        jsonObj = wInf;
        isNull();
    }

    public boolean isNull() {
        if (jsonObj == null)
            return true;
        else {
            try {
                jsonArray = jsonObj.getJSONArray("list");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public void setNumberInArr(int num) {
        numberInArray = num;
    }

    public int getListSize() {
        int cnt = 0;
        try {
            cnt = jsonObj.getInt("cnt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cnt;
    }

    public String getCountry() {
        JSONObject jsonCity = null;
        try {
            jsonCity = jsonObj.getJSONObject("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String coutry = "";
        try {
            coutry = jsonCity.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coutry;
    }

    public String getCity() {
        String city = "";
        JSONObject jsonCity = null;
        try {
            jsonCity = jsonObj.getJSONObject("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            city = jsonCity.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return city;
    }

    public String getDate() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String date = "";
        try {
            date = jsonDay.getString("dt_txt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        date = date.substring(0, 10);
        date = date.substring(8) + "." + date.substring(5, 7) + "." + date.substring(0, 4);
        return date;
    }

    public String getTime() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String time = "";
        try {
            time = jsonDay.getString("dt_txt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        time = time.substring(11);
        return time;
    }

    public double getTemp() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonMain = null;
        try {
            jsonMain = jsonDay.getJSONObject("main");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        double temp = -1;
        try {
            temp = jsonMain.getDouble("temp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }
    public long getPressure() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonMain = null;
        try {
            jsonMain = jsonDay.getJSONObject("main");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int pressure = -1;
        try {
            pressure = jsonMain.getInt("pressure");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Math.round(pressure * toMmHg);
    }
    public double getWindSpeed() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonWind = null;
        try {
            jsonWind = jsonDay.getJSONObject("wind");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        double windSpeed = -1;
        try {
            windSpeed = jsonWind.getDouble("speed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return windSpeed;
    }
    public String getWindDegree() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonWind = null;
        try {
            jsonWind = jsonDay.getJSONObject("wind");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int windDeg = -1;
        try {
            windDeg = jsonWind.getInt("deg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return windDegree(windDeg);
    }

    private String windDegree(int angle) {
        String windDegree;
        if (angle >= 0 && angle < 20 || (angle >= 340 && angle <= 360)) {
            windDegree = "C";
        }
        else if (angle < 70) {
            windDegree = "СВ";
        }
        else if (angle < 110) {
            windDegree = "B";
        }
        else if (angle < 160) {
            windDegree = "ЮВ";
        }
        else if (angle < 200) {
            windDegree = "Ю";
        }
        else if (angle < 250) {
            windDegree = "ЮЗ";
        }
        else if (angle < 290) {
            windDegree = "З";
        }
        else
            windDegree = "СЗ";
        return windDegree;
    }

    public String getCurrentWeather() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray weather = null;
        try {
            weather = jsonDay.getJSONArray("weather");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject currentWeather = null;
        try {
            currentWeather = weather.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String currWeather = "";
        try {
            currWeather = currentWeather.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currWeather;
    }

    public String getIconURL() {
        try {
            jsonDay = jsonArray.getJSONObject(numberInArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray weather = null;
        try {
            weather = jsonDay.getJSONArray("weather");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject ico = null;
        try {
            ico = weather.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String icon = "";
        try {
            icon = ico.getString("icon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return icon;
    }
}
