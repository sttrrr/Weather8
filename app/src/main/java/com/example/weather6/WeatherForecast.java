package com.example.weather6;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
      private  TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ImageView icon;
    private ImageView icon2;
    private ImageView icon3;
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
        icon = findViewById(R.id.forecast_icon);
        icon2 = findViewById(R.id.forecast_icon1);
        icon3 = findViewById(R.id.forecast_icon2);
        temp_min1 = findViewById(R.id.min_temp_forecast);
        temp_min2 = findViewById(R.id.min_temp_forecast1);
        temp_min3 = findViewById(R.id.min_temp_forecast2);
        temp_max1 = findViewById(R.id.max_temp_forecast);
        temp_max2 = findViewById(R.id.max_temp_forecast1);
        temp_max3 = findViewById(R.id.max_temp_forecast2);

        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+cityWeather+"&appid=12eeaa685d80aa4c20d82c86c12eb536&cnt=1&lang=ru&units=metric";

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
                JSONObject object1 = array.getJSONObject(1);
                JSONObject object2 = array.getJSONObject(2);
                JSONObject main = object.getJSONObject("main");

               // JSONArray weather = object.optJSONArray("weather");
               // JSONObject object1 = weather.getJSONObject(0);


                int temp = json.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp");
                int temp_min = json.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp_min");;
                int temp_max = json.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp_max");
                int temp1 = json.getJSONArray("list").getJSONObject(1).getJSONObject("main").getInt("temp");
                int temp_min11 = json.getJSONArray("list").getJSONObject(1).getJSONObject("main").getInt("temp_min");;
                int temp_max12 = json.getJSONArray("list").getJSONObject(1).getJSONObject("main").getInt("temp_max");
                int temp21= json.getJSONArray("list").getJSONObject(2).getJSONObject("main").getInt("temp");
                int temp_min22 = json.getJSONArray("list").getJSONObject(2).getJSONObject("main").getInt("temp_min");;
                int temp_max23 = json.getJSONArray("list").getJSONObject(2).getJSONObject("main").getInt("temp_max");

                String iconNum1 = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                String iconNum2 = json.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("icon");
                String iconNum3 = json.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("icon");



                textView1.setText(temp + "°C");
                temp_min1.setText(temp_min+"°C");
                temp_max1.setText(temp_max+"°C");
                textView2.setText(temp1 + "°C");
                temp_min2.setText(temp_min11 +"°C");
                temp_max2.setText(temp_max12+"°C");
                textView3.setText(temp21+"°C");
                temp_min3.setText(temp_min22+"°C");
                temp_max3.setText(temp_max23+"°C");


                //вывод картинки в зависимости от погоды
                seticon(iconNum1,icon);
                seticon(iconNum2,icon2);
                seticon(iconNum3,icon3);

} catch (JSONException e) {
                e.printStackTrace();
            }}}
public void seticon(String num, ImageView icon){
    switch(num)
    {
        case "01d":
            icon.setImageResource(R.drawable.d01);
            break;
        case "01n":
            icon.setImageResource(R.drawable.n01);
            break;
        case "02d":
            icon.setImageResource(R.drawable.d02);
            break;
        case "02n":
            icon.setImageResource(R.drawable.n02);
            break;
        case "03d":
            icon.setImageResource(R.drawable.d03);
            break;
        case "03n":
           icon.setImageResource(R.drawable.d03);
            break;
        case "04n":
            icon.setImageResource(R.drawable.d04);
            break;
        case "04d":
            icon.setImageResource(R.drawable.d04);
            break;
        case "09n":
            icon.setImageResource(R.drawable.d09);
            break;
        case "09d":
            icon.setImageResource(R.drawable.d09);
            break;
        case "10n":
            icon.setImageResource(R.drawable.d10);
            break;
        case "10d":
            icon.setImageResource(R.drawable.d10);
            break;
        case "11n":
            icon.setImageResource(R.drawable.d11);
            break;
        case "11d":
           icon.setImageResource(R.drawable.d11);
            break;
        case "13n":
            icon.setImageResource(R.drawable.d13);
            break;
        case "13d":
            icon.setImageResource(R.drawable.d13);
            break;
        case "50n":
           icon.setImageResource(R.drawable.d50);
            break;
        case "50d":
            icon.setImageResource(R.drawable.d50);
            break;
    }

}
}



