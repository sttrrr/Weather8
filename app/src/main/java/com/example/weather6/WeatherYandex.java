package com.example.weather6;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherYandex extends AppCompatActivity {
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
    private TextView textViewAdvice;
    private  TextView descMinTemp;
    private TextView descMaxTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);



        String cityWeather = getIntent().getStringExtra("city");

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
        descMinTemp = findViewById(R.id.min_desc);
        descMaxTemp = findViewById(R.id.max_desc);
        //textViewAdvice = findViewById(R.id.advice_text);
        //textViewCity.setText(cityWeather);



        String url = "https://api.weatherapi.com/v1/current.json?key=31b6f87fed5b4159b6c190704222105&lang=ru&q="+cityWeather+"&aqi=yes";

        new GetURLData().execute(url);





    }

    @SuppressLint("StaticFieldLeak")
    public class GetURLData extends AsyncTask<String, String, String> {

        // Будет выполнено до отправки данных по URL
        protected void onPreExecute() {
            super.onPreExecute();
            textViewTemp.setText("Ожидайте...");
        }
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
                JSONObject current = json.getJSONObject("current");
                int Temperature = current.getInt("temp_c");
                String description = current.getJSONObject("condition").getString("text");
                int windSpeed = current.getInt("wind_mph");
                int Pressure = current.getInt("pressure_mb");
                int Humidity = current.getInt("humidity");
                int likeTemp = current.getInt("feelslike_c");
                int minTemp = current.getInt("gust_mph");
                int maxTemp = current.getInt("precip_mm");
                String icon = current.getJSONObject("condition").getString("code");
                descMinTemp.setText("Порывы ветра");
                descMaxTemp.setText("Осадки");


//выводим все это на экран
                textViewDescription.setText(description);
                textViewTemp.setText(Temperature + "°C");
                textViewWindText.setText( windSpeed+ " м/с");
                textViewHundText.setText(Humidity + "%");
                textViewRainText.setText((int)(Pressure*0.75) + " мм.рт.ст");
                textViewLike.setText(likeTemp + "°C");
                textViewMaxTemp.setText(maxTemp + "мм");
                textViewMinTemp.setText(minTemp + "м/с");


                switch(icon)
                {
                    case "1000":
                        textViewMainIcon.setImageResource(R.drawable.d01);
                        break;
                    case "1003":
                        textViewMainIcon.setImageResource(R.drawable.d04);
                        break;

                    case "1006":
                        textViewMainIcon.setImageResource(R.drawable.d03);
                        break;
                    case "1009":
                        textViewMainIcon.setImageResource(R.drawable.d03);
                        break;
                    case "04n":
                        textViewMainIcon.setImageResource(R.drawable.d04);
                        break;
                    case "04d":
                        textViewMainIcon.setImageResource(R.drawable.d04);
                        break;
                    case "1063":
                        textViewMainIcon.setImageResource(R.drawable.d09);
                        break;
                    case "09d":
                        textViewMainIcon.setImageResource(R.drawable.d09);
                        break;
                    case "1195":
                        textViewMainIcon.setImageResource(R.drawable.d10);
                        break;
                    case "1183":
                        textViewMainIcon.setImageResource(R.drawable.d10);
                        break;
                    case "1087":
                        textViewMainIcon.setImageResource(R.drawable.d11);
                        break;

                    case "1069":
                        textViewMainIcon.setImageResource(R.drawable.d13);
                        break;
                    case "1066":
                        textViewMainIcon.setImageResource(R.drawable.d13);
                        break;
                    case "1117":
                        textViewMainIcon.setImageResource(R.drawable.d50);
                        break;
                    case "1030":
                        textViewMainIcon.setImageResource(R.drawable.d50);
                        break;
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }






}