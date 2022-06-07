package com.example.weather6;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
      private  TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ImageView icon1;
    private ImageView icon2;
    private ImageView icon3;
    private TextView temp1;
    private TextView temp2;
    private TextView temp3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        String cityWeather = getIntent().getStringExtra("city");
        //инициализация переменных
        textView1 = findViewById(R.id.date);
        textView2 = findViewById(R.id.date1);
        textView3 = findViewById(R.id.date2);
        icon1 = findViewById(R.id.forecast_icon);
        icon2 = findViewById(R.id.forecast_icon1);
        icon3 = findViewById(R.id.forecast_icon2);
        temp1 = findViewById(R.id.temp);
        temp2= findViewById(R.id.temp1);
        temp3= findViewById(R.id.temp2);


        String url = "https://api.weather.yandex.ru/v2/forecast?q="+cityWeather+"&hours=true&limit=5&lang=ru_RU";
        //String url = "https://api.openweathermap.org/data/2.5/weather?q="+ cityWeather+"&appid=4d414a5f570776be9b49ec722a459a33&units=metric&lang=ru";
        //String url = "https://api.openweathermap.org/data/2.5/forecast?q="+ cityWeather+"&appid=4d414a5f570776be9b49ec722a459a33&cnt=3&units=metric";


        new GetURLData().execute(url);
    }


    public class GetURLData extends AsyncTask<String, String, String> {


        // Будет выполняться во время подключения по URL
        @Override
        public String doInBackground(@NonNull String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                // Создаем URL подключение, а также HTTP подключение
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("X-Yandex-API-Key","19c1d270-aad8-46f7-b9c7-dc1a7a966085");
                connection.connect();
                connection.setRequestMethod("POST");


                // Создаем объекты для считывания данных из файла
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                // Генерируемая строка
                StringBuilder buffer = new StringBuilder();
                String line = "";

                // Считываем файл и записываем все в строку
                while((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                // Возвращаем строку
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Закрываем соединения
                if(connection != null)
                    connection.disconnect();

                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        // Выполняется после завершения получения данных
        @RequiresApi(api = Build.VERSION_CODES.M)
        @SuppressLint({"SetTextI18n", "CheckResult"})
        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject json = new JSONObject(result);
                JSONObject fact = json.getJSONObject("fact");
                JSONArray forecast = json.getJSONArray("forecasts");
                JSONObject parts1 = forecast.getJSONObject(0).getJSONObject("parts");
                JSONObject parts2 = forecast.getJSONObject(1).getJSONObject("parts");
                JSONObject parts3 = forecast.getJSONObject(2).getJSONObject("parts");
                //JSONObject hours1 = forecast.getJSONObject(0).getJSONArray("hours").getJSONObject(0);
               // JSONObject hours2 = forecast.getJSONObject(1).getJSONArray("hours").getJSONObject(0);
               // JSONObject hours3 = forecast.getJSONObject(0).getJSONArray("hours").getJSONObject(0);

                String date = forecast.getJSONObject(0).getString("date");
                String date1 = forecast.getJSONObject(1).getString("date");
                String date2 = forecast.getJSONObject(2).getString("date");

                int temp = parts1.getJSONObject("day").getInt("temp");
                int temp_1 = parts2.getJSONObject("day").getInt("temp");
                int temp_2 = parts3.getJSONObject("day").getInt("temp");





                //JSONObject night = parts.getJSONObject("night");
               // JSONObject day = parts.getJSONObject("day_short");
              //  int temp_min = night.getInt("temp_min");
               // int temp_max = day.getInt("temp");
                String description1 = parts1.getJSONObject("day_short").getString("condition");
                String description2 = parts2.getJSONObject("day_short").getString("condition");
                String description3 = parts3.getJSONObject("day_short").getString("condition");
                textView1.setText(date);
                textView2.setText(date1);
                textView3.setText(date2);
                OpenWeather.seticon(description1,icon1);
                OpenWeather.seticon(description2,icon2);
                OpenWeather.seticon(description3,icon3);
                temp1.setText(temp);
                temp2.setText(temp_1);
                temp3.setText(temp_2);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }}}



