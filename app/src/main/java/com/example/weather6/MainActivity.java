package com.example.weather6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText citySearch;
static String city;
    Button btnOpenWeather;
    Button btnY ;
    Button btnG;


    public static String getCity() {
        return city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if(citySearch.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.errorName, Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(this, WeatherYandex.class);
            intent.putExtra("city",citySearch.getText().toString());
            startActivity(intent);


    }}

    public void btnGonClick(View view) {
        citySearch = findViewById(R.id.editTextSearch);
        if(citySearch.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.errorName, Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(this, WeatherForecast.class);
            intent.putExtra("city",citySearch.getText().toString());
            startActivity(intent);
    }

}



}