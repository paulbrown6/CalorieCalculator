package com.pb.app.caloriecalculator.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pb.app.caloriecalculator.entity.ProductsEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateResponse {

    private String dateProducts;
    private ArrayList<String> datesStatistic;
    private MutableLiveData<ArrayList<String>> datesLiveData = new MutableLiveData<>();

    private ArrayList<String> arrDay;
    private ArrayList<String> arrNumDay;
    private String todayDate;
    private String todayDay;
    private String yesterdayDate;
    private String yesterdayDay;
    private String befyesterdayDate;
    private String befyesterdayDay;

    public enum Date{
        BEFOREYESTERDAY,
        YESTERDAY,
        TODAY;
    }

    private static DateResponse instance;

    public static DateResponse getInstance(){
        if(instance == null) instance = new DateResponse();
        return instance;
    }

    private DateResponse(){
        arrDay = new ArrayList<String>();
        arrNumDay = new ArrayList<String>();
        todayDate = dateFormat(new java.util.Date());
        todayDay = dayFormat(new java.util.Date());
        yesterdayDate = dateFormat(new java.util.Date(new java.util.Date().getTime() - 86400000));
        yesterdayDay = dayFormat(new java.util.Date(new java.util.Date().getTime() - 86400000));
        befyesterdayDate = dateFormat(new java.util.Date(new java.util.Date().getTime() - 172800000));
        befyesterdayDay = dayFormat(new java.util.Date(new java.util.Date().getTime() - 172800000));
        dateProducts = todayDate;
        arrDay.add(befyesterdayDay);
        arrDay.add(yesterdayDay);
        arrDay.add(todayDay);
        arrNumDay.add(befyesterdayDate.substring(0,2));
        arrNumDay.add(yesterdayDate.substring(0,2));
        arrNumDay.add(todayDate.substring(0,2));
        arrDay.add(dayFormat(new java.util.Date(new java.util.Date().getTime() + 86400000)));
        arrNumDay.add(dateFormat(new java.util.Date(new java.util.Date().getTime() + 86400000)).substring(0,2));
        arrDay.add(dayFormat(new java.util.Date(new java.util.Date().getTime() + 172800000)));
        arrNumDay.add(dateFormat(new java.util.Date(new java.util.Date().getTime() + 172800000)).substring(0,2));
        datesStatistic = new ArrayList<>();
        datesStatistic.add(yesterdayDate);
        datesStatistic.add(todayDate);
    }

    private String dateFormat(java.util.Date date){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd-MM-yyyy");
        return formatForDateNow.format(date);
    }

    private String dayFormat(java.util.Date date){
        SimpleDateFormat formatDay = new SimpleDateFormat("E");
        return formatDay.format(date).substring(0,2);
    }

    public java.util.Date stringToDate(String sDate){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return format.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new java.util.Date();
        }
    }

    public void updateDate(Date date){
        switch (date){
            case TODAY: dateProducts = todayDate;
                        break;
            case YESTERDAY: dateProducts = yesterdayDate;
                            break;
            case BEFOREYESTERDAY: dateProducts = befyesterdayDate;
                                    break;
        }
    }

    public void updateDateStatistic(java.util.Date dateOne, java.util.Date dateTwo){
        if (dateOne != null && dateTwo != null) {
            datesStatistic = new ArrayList<>();
            datesStatistic.add(dateFormat(dateOne));
            datesStatistic.add(dateFormat(dateTwo));
            datesLiveData.setValue(datesStatistic);
        }
    }

    public boolean isDateToday(){
        return dateProducts.equals(todayDate);
    }

    public String getDateProducts(){
        return dateProducts;
    }

    public ArrayList<String> getArrDay() {
        return arrDay;
    }

    public ArrayList<String> getArrNumDay() {
        return arrNumDay;
    }

    public ArrayList<String> getDatesStatistic(){
        return datesStatistic;
    }

    public LiveData<ArrayList<String>> getDateEntityState(){
        return datesLiveData;
    }
}
