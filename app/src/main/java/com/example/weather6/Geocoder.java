package com.example.weather6;


import static android.net.wifi.WifiConfiguration.Status.strings;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Geocoder {


    String cityWeather;

    public Geocoder(String cityWeather){
        this.cityWeather = cityWeather;
    }
String url = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=0f9c671e-c7f0-4da2-90f8-e6a9faaaee13&geocode="+this.cityWeather;
    //String url = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=0f9c671e-c7f0-4da2-90f8-e6a9faaaee13&geocode=" + cityWeather;


    }
