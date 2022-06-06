package com.example.weather6;

import androidx.annotation.NonNull;
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

public class OpenWeather extends AppCompatActivity {
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
        //textViewAdvice = findViewById(R.id.advice_text);


        String url = "https://api.openweathermap.org/data/2.5/weather?q="+ cityWeather+"&appid=4d414a5f570776be9b49ec722a459a33&units=metric&lang=ru";
        //String url = "https://api.openweathermap.org/data/2.5/forecast?q="+ cityWeather+"&appid=4d414a5f570776be9b49ec722a459a33&cnt=3&units=metric";
        new GetURLData().execute(url);





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

        // Выполняется после завершения получения данных
        @RequiresApi(api = Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n")
        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("weather");
                JSONObject object = array.getJSONObject(0);
                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                String description = object.getString("description");
                String icons = object.getString("icon");
                JSONObject main = json.getJSONObject("main");
//температура

                int Temperature = main.getInt("temp");
                //влажность
                int Humidity = main.getInt("humidity");
//давление
                int Pressure = main.getInt("pressure");
//скорость ветра
                JSONObject wind = json.getJSONObject("wind");
                int windSpeed = wind.getInt("speed");
                //максимальная и минимальная температура
                int maxTemp = main.getInt("temp_max");
                int minTemp = main.getInt("temp_min");
                int likeTemp = main.getInt("feels_like");

//выводим все это на экран
                textViewTemp.setText(Temperature + "°C");
                textViewWindText.setText( windSpeed+ " м/с");
                textViewHundText.setText(Humidity + "%");
                textViewRainText.setText((int)(Pressure*0.75) + " мм.рт.ст");
                textViewDescription.setText(details.getString("description"));
                textViewLike.setText(likeTemp + "°C");
                textViewMaxTemp.setText(maxTemp + "°C");
                textViewMinTemp.setText(minTemp + "°C");


                //вывод картинки в зависимости от погоды
                switch(object.getString("icon"))
                {
                    case "01d":
                        textViewMainIcon.setImageResource(R.drawable.d01);
                        break;
                    case "01n":
                        textViewMainIcon.setImageResource(R.drawable.n01);
                        break;
                    case "02d":
                        textViewMainIcon.setImageResource(R.drawable.d02);
                        break;
                    case "02n":
                        textViewMainIcon.setImageResource(R.drawable.n02);
                        break;
                    case "03d":
                        textViewMainIcon.setImageResource(R.drawable.d03);
                        break;
                    case "03n":
                        textViewMainIcon.setImageResource(R.drawable.d03);
                        break;
                    case "04n":
                        textViewMainIcon.setImageResource(R.drawable.d04);
                        break;
                    case "04d":
                        textViewMainIcon.setImageResource(R.drawable.d04);
                        break;
                    case "09n":
                        textViewMainIcon.setImageResource(R.drawable.d09);
                        break;
                    case "09d":
                        textViewMainIcon.setImageResource(R.drawable.d09);
                        break;
                    case "10n":
                        textViewMainIcon.setImageResource(R.drawable.d10);
                        break;
                    case "10d":
                        textViewMainIcon.setImageResource(R.drawable.d10);
                        break;
                    case "11n":
                        textViewMainIcon.setImageResource(R.drawable.d11);
                        break;
                    case "11d":
                        textViewMainIcon.setImageResource(R.drawable.d11);
                        break;
                    case "13n":
                        textViewMainIcon.setImageResource(R.drawable.d13);
                        break;
                    case "13d":
                        textViewMainIcon.setImageResource(R.drawable.d13);
                        break;
                    case "50n":
                        textViewMainIcon.setImageResource(R.drawable.d50);
                        break;
                    case "50d":
                        textViewMainIcon.setImageResource(R.drawable.d50);
                        break;
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }






}





