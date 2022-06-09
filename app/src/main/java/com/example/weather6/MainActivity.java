package com.example.weather6;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
EditText citySearch;
String city;
    Button btnOpenWeather;
    Button btnY ;
    Button btnG;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
final EditText citySe = findViewById(R.id.editTextSearch);
        city = citySe.toString();
        btnOpenWeather = (Button) findViewById(R.id.buttonOW);
        btnY = findViewById(R.id.buttonY);
         btnG = findViewById(R.id.buttonForecast);


    }


        public void btnOWonClick(View view) {
        citySearch = findViewById(R.id.editTextSearch);
        if(citySearch.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.errorName, Toast.LENGTH_LONG).show();
        else {
        Intent intent = new Intent(this, OpenWeather.class);
        intent.putExtra("city",citySearch.getText().toString());
        startActivity(intent);


    }}

    public void btnYonClick(View view) {
        citySearch = findViewById(R.id.editTextSearch);
        city = citySearch.toString();
        if(citySearch.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.errorName, Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(this, WeatherYandex.class);
            intent.putExtra("city",citySearch.getText().toString());
            startActivity(intent);


    }}

    public void btnGonClick(View view) {
        citySearch = findViewById(R.id.editTextSearch);
        city = citySearch.toString();
        if (citySearch.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.errorName, Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(this, WeatherForecast.class);
            intent.putExtra("city", citySearch.getText().toString());
            startActivity(intent);
        }

    }


    public void btnHelpClick(View view) {
        RelativeLayout layout = findViewById(R.id.relative);
        layout.setVisibility(layout.VISIBLE);
    }
}