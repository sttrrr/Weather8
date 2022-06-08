package com.example.weather6;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class GetURLData1 extends AsyncTask<String, String, String> {


    String url = "https://geocode-maps.yandex.ru/1.x/?apikey=0f9c671e-c7f0-4da2-90f8-e6a9faaaee13&geocode=Краснодар";

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
    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject json = new JSONObject(result);

        }

}
