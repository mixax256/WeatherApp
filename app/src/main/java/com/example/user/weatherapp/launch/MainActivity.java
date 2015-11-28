package com.example.user.weatherapp.launch;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.user.weatherapp.R;
import com.example.user.weatherapp.utils.API;
import com.example.user.weatherapp.utils.DBaseInfo;
import com.meetme.android.horizontallistview.HorizontalListView;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final static String GET_ON_CITY = "city";
    final static String GET_ON_COORDS = "coords";
    final static int SET_TEMP_METRIC = 1;
    private LocationManager locationManager;
    private double lat, lon;
    private boolean inCelsius = true;
    private DBaseInfo fb;

    private Update mTask = new Update();
    private Drawer.Result drawerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerInit(toolbar);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false)
            startActivity(new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            mTask.execute(GET_ON_COORDS, String.valueOf(lat), String.valueOf(lon));
        }
        else
            mTask.execute(GET_ON_CITY, "Rostov-on-Don");
    }

    private void drawerInit(Toolbar toolbar) {
        drawerResult = new Drawer()
            .withActivity(this)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withHeader(R.layout.drawer_header)
            .addDrawerItems(
                new SectionDrawerItem().withName(R.string.drawer_item_settings),
                new PrimaryDrawerItem().withName(R.string.drawer_item_set_city).withIcon(FontAwesome.Icon.faw_building).withIdentifier(1),
                new PrimaryDrawerItem().withName(R.string.drawer_item_set_temp_metric).withIcon(FontAwesome.Icon.faw_bullseye),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_question).withIdentifier(1),
                new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(1)
            )
            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                    String calledItem = MainActivity.this.getString(((Nameable) drawerItem).getNameRes());
                    if (calledItem.equals(getString(R.string.drawer_item_set_temp_metric))) {
                        showDialog(SET_TEMP_METRIC);
                    }
                    if (calledItem.equals(getString(R.string.drawer_item_set_city))) {
                        CityInputDialog cityInputDialog = new CityInputDialog(MainActivity.this, new Update());
                        cityInputDialog.setTitle("Введите название города");
                        cityInputDialog.show();
                    }
                    if (calledItem.equals(getString(R.string.drawer_item_about))) {
                        AboutDialog aboutDialog = new AboutDialog(MainActivity.this);
                        aboutDialog.setTitle(calledItem);
                        aboutDialog.show();
                    }
                    if (calledItem.equals(getString(R.string.drawer_item_help))) {
                        HelpDialog helpDialog = new HelpDialog(MainActivity.this);
                        helpDialog.setTitle(calledItem);
                        helpDialog.show();
                    }
                }
        }).build();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        switch (id) {
            case SET_TEMP_METRIC:
                final double[] temperature = new double[1];
                builder.setTitle("Отображать погоду в градусах")
                        .setCancelable(false)
                        .setNegativeButton("Назад",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                TextView currTemp = (TextView) findViewById(R.id.currTemp);
                                if (Math.abs(temperature[0]) > 9)
                                    if (temperature[0] > 0) {
                                        currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 130);
                                        currTemp.setPadding(0, 60, 0, 0);
                                    } else {
                                        currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 100);
                                        currTemp.setPadding(0, 60, 0, 0);
                                    }
                                else if (temperature[0] < 0) {
                                    currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 170);
                                    currTemp.setPadding(0, 0, 0, 0);
                                } else {
                                    currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 200);
                                    currTemp.setPadding(0, 0, 0, 0);
                                }
                                currTemp.setText(Math.round(temperature[0]) + " ");
                                FillList(fb);
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(new String[]{"Цельсия", "Фаренгейта"}, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                TextView currTemp = (TextView) findViewById(R.id.currTemp);
                                double temp = Double.parseDouble(currTemp.getText().toString());
                                if (item == 1) {
                                    if (inCelsius == true) {
                                        temp = toFahrenheit(temp);
                                        inCelsius = false;
                                    }
                                } else {
                                    if (inCelsius == false) {
                                        temp = toCelsium(temp);
                                        inCelsius = true;
                                    }
                                }
                                temperature[0] = temp;
                            }
                        });
                return builder.create();
            default:
                return null;
        }
    }

    private double toCelsium(double temp) {
        return Math.round(temp * 1.8 + 32);
    }
    private double toFahrenheit(double temp) {
        return Math.round((temp - 32) * 5 / 9);
    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView city = (TextView) findViewById(R.id.city);
        if (id == R.id.update) {
            Update upd = new Update();
            upd.execute(GET_ON_CITY, city.getText().toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Update extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            API.ApiResponse ap;
            if (params[0].equals(GET_ON_CITY))
                ap = API.execute("forecast", API.HttpMethod.GET, "q", params[1], "lang", "ru","type", "like", "units",
                        "metric", "APPID", "532daa806f83560bfa5978caf25db6c3");
            else
                ap = API.execute("forecast", API.HttpMethod.GET, "lat", params[1], "lon", params[2],
                        "lang", "ru","type", "like", "units",
                        "metric", "APPID", "532daa806f83560bfa5978caf25db6c3");
            JSONObject result;
            result = ap.getJson();
            return result;
        }
        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            TextView grad = (TextView) findViewById(R.id.grad);
            TextView city = (TextView) findViewById(R.id.city);
            TextView country = (TextView) findViewById(R.id.country);
            TextView currTemp = (TextView) findViewById(R.id.currTemp);
            TextView pressure = (TextView) findViewById(R.id.pressure);
            TextView windSpeed = (TextView) findViewById(R.id.wind_speed);
            TextView windDeg = (TextView) findViewById(R.id.wind_deg);
            TextView currWeather = (TextView) findViewById(R.id.curr_weather);
            ImageView ico = (ImageView) findViewById(R.id.imgWeather);

            fb = new DBaseInfo(result, MainActivity.this);
            FillList(fb);

            city.setText(fb.getCity());
            country.setText(fb.getCountry());

            double temp = fb.getCurrTemp();
            if (inCelsius == false)
                temp = toFahrenheit(temp);

            if (Math.abs(temp) > 9)
                if (temp > 0) {
                    currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 130);
                    currTemp.setPadding(0, 60, 0, 0);
                }
                else {
                    currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 100);
                    currTemp.setPadding(0, 60, 0, 0);
                }
            else
            if (temp < 0) {
                currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 170);
                currTemp.setPadding(0, 0, 0, 0);
            }
            else {
                currTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 200);
                currTemp.setPadding(0, 0, 0, 0);
            }

            grad.setVisibility(View.VISIBLE);
            currTemp.setText(Math.round(temp) + " ");
            pressure.setText(getString(R.string.pressure) + "\n" +
                    Math.round(fb.getCurrPressure()) + " " + getString(R.string.mmHg));
            windSpeed.setText(getString(R.string.windSpeed) + " " +
                    Math.round(fb.getWindSpeed()) + " " + getString(R.string.meterInSecond));
            windDeg.setText(getString(R.string.windDeg) + " " + fb.getWindDegree());
            currWeather.setText(fb.getCurrentWeather());
            ico.setImageResource(fb.getIconID());
        }
    }

    private String[] setMinMaxTemps(String[] dates, DBaseInfo fb) {
        String[] minMaxTemps = new String[dates.length];
        for (int i = 0; i < minMaxTemps.length; i++)
            minMaxTemps[i] = fb.getMinMaxTemp(dates[i], inCelsius);
        return minMaxTemps;
    }

    private int[] setIcos(String[] dates, DBaseInfo fb) {
        int[] icos = new int[dates.length];
        for (int i = 0; i < icos.length; i++)
            icos[i] = fb.getIconAtDate(dates[i]);
        return icos;
    }

    private void FillList(DBaseInfo fb) {
        String[] dates;
        String[] minMaxTemps;
        int[] icos;

        dates = fb.getDates();
        minMaxTemps = setMinMaxTemps(dates, fb);
        icos = setIcos(dates, fb);

        ArrayList<Map<String, Object>> data = new ArrayList<>(dates.length);
        Map<String, Object> m;

        for (int i = 0; i < dates.length; i++) {
            m = new HashMap<>();
            m.put("date", dates[i]);
            m.put("img", icos[i]);
            m.put("min_max_temp", minMaxTemps[i]);
            data.add(m);
        }

        String[] from = new String[] { "date", "img", "min_max_temp" };
        int[] to = new int[] { R.id.date, R.id.ico_at_day, R.id.min_max_temp };

        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, data, R.layout.list_item, from, to);

        HorizontalListView lvSimple = (HorizontalListView) findViewById(R.id.listView);
        lvSimple.setAdapter(simpleAdapter);
    }
}
