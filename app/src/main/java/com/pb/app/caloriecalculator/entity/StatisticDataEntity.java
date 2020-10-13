package com.pb.app.caloriecalculator.entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatisticDataEntity {

    @SerializedName("dates")
    @Expose
    private List<String> data;

    @SerializedName("calories")
    @Expose
    private List<Integer> calories;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<Integer> getCalories() {
        return calories;
    }

    public void setCalories(List<Integer> calories) {
        this.calories = calories;
    }
}