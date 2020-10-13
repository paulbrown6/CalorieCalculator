package com.pb.app.caloriecalculator.api;

import android.app.Application;

import com.pb.app.caloriecalculator.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private Api api;
    private Retrofit retrofit;

    private static App instance;

    public App(){
        retrofit = new Retrofit.Builder().baseUrl("https://api.calorie-calculator.ru/api/").addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(Api.class);
    }

    public static App getInstance(){
        if(instance == null) instance = new App();
        return instance;
    }

    public Api getApi() {
        return api;
    }
}
