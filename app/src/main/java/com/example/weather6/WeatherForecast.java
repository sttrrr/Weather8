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
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private TextView temp_min1;
    private TextView temp_min2;
    private TextView temp_min3;
    private TextView temp_max1;
    private TextView temp_max2;
    private TextView temp_max3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        String cityWeather = getIntent().getStringExtra("city");
        //инициализация переменных
        textView1 = findViewById(R.id.date);
        textView2 = findViewById(R.id.date1);
        textView3 = findViewById(R.id.date2);
        imageView1 = findViewById(R.id.forecast_icon);
        imageView2 = findViewById(R.id.forecast_icon1);
        imageView3 = findViewById(R.id.forecast_icon2);
        temp_min1 = findViewById(R.id.min_temp_forecast);
        temp_min2 = findViewById(R.id.min_temp_forecast1);
        temp_min3 = findViewById(R.id.min_temp_forecast2);
        temp_max1 = findViewById(R.id.max_temp_forecast);
        temp_max2 = findViewById(R.id.max_temp_forecast1);
        temp_max3 = findViewById(R.id.max_temp_forecast2);

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Krasnodar&appid=12eeaa685d80aa4c20d82c86c12eb536&cnt=1&lang=ru&units=metric";

        new GetURLData().execute(url);
    }

        @SuppressLint("StaticFieldLeak")
        public class GetURLData extends AsyncTask<String, String, String> {

            // Будет выполняться во время подключения по URL
            @Override
            public String doInBackground(String... strings) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    // Создаем URL подключение, а также HTTP подключение
                    URL url = new URL(strings[0]);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.connect();

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
        @SuppressLint("SetTextI18n")
        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("list");
                JSONObject object = array.getJSONObject(0);
                JSONObject main = object.getJSONObject("main");
                JSONArray weather = object.optJSONArray("weather");
                JSONObject object1 = weather.getJSONObject(0);


                int temp = main.getInt("temp");
                int temp_min = main.getInt("temp_min");
                int temp_max = main.getInt("temp_max");

                textView1.setText(temp);
                temp_min1.setText(temp_min);
                temp_max1.setText(temp_max);

                //вывод картинки в зависимости от погоды
                switch(object1.getString("icon"))
                {
                    case "01d":
                        imageView1.setImageResource(R.drawable.d01);
                        break;
                    case "01n":
                        imageView1.setImageResource(R.drawable.n01);
                        break;
                    case "02d":
                        imageView1.setImageResource(R.drawable.d02);
                        break;
                    case "02n":
                        imageView1.setImageResource(R.drawable.n02);
                        break;
                    case "03d":
                        imageView1.setImageResource(R.drawable.d03);
                        break;
                    case "03n":
                        imageView1.setImageResource(R.drawable.d03);
                        break;
                    case "04n":
                        imageView1.setImageResource(R.drawable.d04);
                        break;
                    case "04d":
                        imageView1.setImageResource(R.drawable.d04);
                        break;
                    case "09n":
                        imageView1.setImageResource(R.drawable.d09);
                        break;
                    case "09d":
                        imageView1.setImageResource(R.drawable.d09);
                        break;
                    case "10n":
                        imageView1.setImageResource(R.drawable.d10);
                        break;
                    case "10d":
                        imageView1.setImageResource(R.drawable.d10);
                        break;
                    case "11n":
                        imageView1.setImageResource(R.drawable.d11);
                        break;
                    case "11d":
                        imageView1.setImageResource(R.drawable.d11);
                        break;
                    case "13n":
                        imageView1.setImageResource(R.drawable.d13);
                        break;
                    case "13d":
                        imageView1.setImageResource(R.drawable.d13);
                        break;
                    case "50n":
                        imageView1.setImageResource(R.drawable.d50);
                        break;
                    case "50d":
                        imageView1.setImageResource(R.drawable.d50);
                        break;
                }

} catch (JSONException e) {
                e.printStackTrace();
            }}}}



