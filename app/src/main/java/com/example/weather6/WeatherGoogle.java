package com.example.weather6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;


public class WeatherGoogle extends AppCompatActivity {

    private TextView textViewCity;
    private TextView textViewTemp;
    private ImageView textViewMainIcon;
    private TextView textViewHundText;
    private TextView textViewRainText;
    private TextView textViewWindText;
    private TextView textViewDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);
        JSONObject json = new JSONObject();
        String cityWeather = getIntent().getStringExtra("city");
        setContentView(R.layout.activity_open_weather);
        textViewMainIcon = findViewById(R.id.weather_icon);
        textViewCity = findViewById(R.id.cityTextBiew);
        textViewTemp = findViewById(R.id.tempWeather);
        textViewCity.setText(cityWeather);
        textViewHundText= findViewById(R.id.humidity_text);
        textViewRainText = findViewById(R.id.pressure_text);
        textViewWindText = findViewById(R.id.wind_text);
        textViewDescription = findViewById(R.id.description);
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+cityWeather+"%2CUK?unitGroup=metric&key=SZWYM66LY3UXC39H57ZBRZWTM&lang=ru";
        new GetURLData().execute(url);


    }

    class GetURLData extends AsyncTask<String, String, String> {

         //Будет выполнено до отправки данных по URL
        /*  protected void onPreExecute() {
          super.onPreExecute();
           textViewTemp.setText("Ожидайте...");
        }*/
        // Будет выполняться во время подключения по URL
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                // Создаем URL подключение, а также HTTP подключение
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection)url.openConnection();

               // connection.setRequestMethod("GET");
                //add request header
                //connection.setRequestProperty("User-Agent", "Yandex-API-Key: 19c1d270-aad8-46f7-b9c7-dc1a7a966085");
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


    }
    }




