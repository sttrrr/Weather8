package com.example.weather6;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class OpenWeather extends AppCompatActivity {
    public static Map<String, String> nameDesc = new HashMap<String, String>();
    private TextView textViewCity;
    private TextView textViewTemp;
    private ImageView textViewMainIcon;
    private TextView textViewHundText;
    private TextView textViewRainText;
    private TextView textViewWindText;
    private TextView textViewDescription;
    private TextView textViewMaxTemp;
    private TextView textViewMinTemp;
    private TextView textViewLike;

public String cityWeather;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);



        cityWeather = getIntent().getStringExtra("city");

        textViewMainIcon = findViewById(R.id.weather_icon);
        textViewCity = findViewById(R.id.cityTextBiew);
        textViewTemp = findViewById(R.id.tempWeather);
        textViewCity.setText(cityWeather);
        textViewHundText= findViewById(R.id.humidity_text);
        textViewRainText = findViewById(R.id.pressure_text);
        textViewWindText = findViewById(R.id.wind_text);
        textViewDescription = findViewById(R.id.description);
        textViewMaxTemp = findViewById(R.id.max_text);
        textViewMinTemp = findViewById(R.id.min_text);
        textViewLike = findViewById(R.id.feels_like_text);

        android.location.Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(cityWeather,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double lat = addresses.get(0).getLatitude();
        Double lon = addresses.get(0).getLongitude();
        String url = "https://api.weather.yandex.ru/v2/forecast?lat="+lat+"&lon="+lon+"&hours=false&limit=5&lang=ru_RU";


        new GetURLData().execute(url);
        nameDesc.put("clear", "Ясно");
        nameDesc.put("partly-cloudy ", "Облачно с прояснениями");
        nameDesc.put("cloudy","Облачно");
        nameDesc.put("overcast","Пасмурно");
        nameDesc.put("drizzle","Морось");
        nameDesc.put("light-rain","Небольшой дождь");
        nameDesc.put("rain","Дождь");
        nameDesc.put("moderate-rain","Умеренно сильный дождь");
        nameDesc.put("continuous-heavy-rain","Долгий сильный дождь");
        nameDesc.put("showers","Ливень");
        nameDesc.put("wet-snow","Дождь со снегом");
        nameDesc.put("snow","Снег");
        nameDesc.put("snow-showers","Снегопад");
        nameDesc.put("hail","Град");
        nameDesc.put("thunderstorm","Гроза");
        nameDesc.put("thunderstorm-with-rain","Дождь с грозой");
        nameDesc.put("thunderstorm-with-hail","Гроза с градом");
    }



    public class GetURLData extends AsyncTask<String, String, String> {

        // Будет выполнено до отправки данных по URL
        protected void onPreExecute() {
            super.onPreExecute();
           textViewTemp.setText("Ожидайте...");
        }
        // Будет выполняться во время подключения по URL
        @Override
        public String doInBackground(@NonNull String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                // Создаем URL подключение, а также HTTP подключение
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("X-Yandex-API-Key","0043f6fb-de83-4a81-a61b-9153b06d451d");
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
                JSONObject parts = forecast.getJSONObject(0).getJSONObject("parts");
                JSONObject night = parts.getJSONObject("night");
                JSONObject day = parts.getJSONObject("day_short");
                int temp_min = night.getInt("temp_min");
                int temp_max = day.getInt("temp");
                String icon = fact.getString("icon");
                int Temperature= fact.getInt("temp");
                int likeTemp = fact.getInt("feels_like");
                int wind_speed = fact.getInt("wind_speed");
                int preassure = fact.getInt("pressure_mm");
                int hudmunity = fact.getInt("humidity");
                String description = fact.getString("condition");
                textViewDescription.setText(nameDesc.get(description));
//выводим все это на экран
                textViewTemp.setText(Temperature + "°C");
                textViewWindText.setText( wind_speed+ " м/с");
                textViewHundText.setText(hudmunity + "%");
                textViewRainText.setText(preassure + " мм.рт.ст");

                textViewLike.setText(likeTemp + "°C");
                textViewMaxTemp.setText(temp_max + "°C");
                textViewMinTemp.setText(temp_min + "°C");
seticon(description,textViewMainIcon);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void seticon(String num, ImageView icon){
        switch(num)
        {
            case "clear":
                icon.setImageResource(R.drawable.clear);
                break;
            case "partly-cloudy":
                icon.setImageResource(R.drawable.partly_cloudy);
                break;
            case "cloudy":
                icon.setImageResource(R.drawable.cloudy);
                break;
            case "overcast":
                icon.setImageResource(R.drawable.overcast);
                break;
            case "drizzle":
                icon.setImageResource(R.drawable.drizzle);
                break;
            case "light-rain":
                icon.setImageResource(R.drawable.light_rain);
                break;
            case "rain":
                icon.setImageResource(R.drawable.rain);
                break;
            case "moderate-rain":
                icon.setImageResource(R.drawable.rain);
                break;
            case "heavy-rain":
                icon.setImageResource(R.drawable.heavy_rain);
                break;
            case "continuous-heavy-rain":
                icon.setImageResource(R.drawable.continous_heavy_rain);
                break;
            case "showers":
                icon.setImageResource(R.drawable.heavy_rain);
                break;
            case "wet-snow":
                icon.setImageResource(R.drawable.wet_snow);
                break;
            case "light-snow":
                icon.setImageResource(R.drawable.snow);
                break;
            case "snow":
                icon.setImageResource(R.drawable.snow);
                break;
            case "snow-showers":
                icon.setImageResource(R.drawable.snow_showers);
                break;
            case "hail":
                icon.setImageResource(R.drawable.hail);
                break;
            case "thunderstorm":
                icon.setImageResource(R.drawable.thunderstorm);
                break;
            case "thunderstorm-with-rain":
                icon.setImageResource(R.drawable.thunderstorm);
                break;
            case "thunderstorm-with-hail":
                icon.setImageResource(R.drawable.thunderstorm);
                break;
        }

    }

    }






