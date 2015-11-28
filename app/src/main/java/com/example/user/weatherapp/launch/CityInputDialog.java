package com.example.user.weatherapp.launch;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.weatherapp.R;

/**
 * Created by user on 26.11.2015.
 */
public class CityInputDialog extends AlertDialog {
    private MainActivity.Update mTask;
    private EditText inputCity;
    private String cityName;

    public CityInputDialog(Context context, MainActivity.Update myAsyncTask) {
        super(context);
        mTask = myAsyncTask;
    }

    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.city_input_dialog);
        inputCity = (EditText) findViewById(R.id.input_city);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button OK = (Button) findViewById(R.id.OK);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName = inputCity.getText().toString();
                mTask.execute(MainActivity.GET_ON_CITY, cityName);
                cancel();
            }
        });
    }
}
