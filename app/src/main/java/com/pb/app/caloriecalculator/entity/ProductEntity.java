package com.pb.app.caloriecalculator.entity;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductEntity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_num")
    @Expose
    private Integer productNum;
    @SerializedName("calorie_num")
    @Expose
    private Integer calorieNum;
    @SerializedName("counting_type")
    @Expose
    private Integer countingType;
    @SerializedName("calorie_total")
    @Expose
    private Integer calorieTotal;

    public ProductEntity(){}

    public ProductEntity(String name, Integer productNum, Integer calorieNum, Integer countingType){
        this.name = name;
        this.productNum = productNum;
        this.calorieNum = calorieNum;
        this.countingType = countingType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getCalorieNum() {
        return calorieNum;
    }

    public void setCalorieNum(Integer calorieNum) {
        this.calorieNum = calorieNum;
    }

    public Integer getCountingType() {
        return countingType;
    }

    public void setCountingType(Integer countingType) {
        this.countingType = countingType;
    }

    public Integer getCalorieTotal() {
        return calorieTotal;
    }

    public void setCalorieTotal(Integer calorieTotal) {
        this.calorieTotal = calorieTotal;
    }

    @NonNull
    public String toString(){
        return id + " " + name + " " + calorieNum + " " + calorieTotal;
    }
}