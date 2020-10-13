package com.pb.app.caloriecalculator.entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticEntity {

    @SerializedName("data")
    @Expose
    private StatisticDataEntity data;

    @SerializedName("count_days")
    @Expose
    private int countDays;

    @SerializedName("count_products")
    @Expose
    private int countProducts;

    @SerializedName("product_num_sum")
    @Expose
    private int productNumSum;

    @SerializedName("avg_products")
    @Expose
    private int avgProducts;

    @SerializedName("calorie_sum")
    @Expose
    private int calorieSum;

    @SerializedName("avg_calories")
    @Expose
    private int avgCalories;

    @SerializedName("biggest_product")
    @Expose
    private ProductEntity biggestProduct;

    @SerializedName("smallest_product")
    @Expose
    private ProductEntity smallestProduct;

    public StatisticDataEntity getData() {
        return data;
    }

    public void setData(StatisticDataEntity data) {
        this.data = data;
    }

    public int getCountDays() {
        return countDays;
    }

    public void setCountDays(int countDays) {
        this.countDays = countDays;
    }

    public int getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }

    public int getProductNumSum() {
        return productNumSum;
    }

    public void setProductNumSum(int productNumSum) {
        this.productNumSum = productNumSum;
    }

    public int getAvgProducts() {
        return avgProducts;
    }

    public void setAvgProducts(int avgProducts) {
        this.avgProducts = avgProducts;
    }

    public int getCalorieSum() {
        return calorieSum;
    }

    public void setCalorieSum(int calorieSum) {
        this.calorieSum = calorieSum;
    }

    public int getAvgCalories() {
        return avgCalories;
    }

    public void setAvgCalories(int avgCalories) {
        this.avgCalories = avgCalories;
    }

    public ProductEntity getBiggestProduct() {
        return biggestProduct;
    }

    public void setBiggestProduct(ProductEntity biggestProduct) {
        this.biggestProduct = biggestProduct;
    }

    public ProductEntity getSmallestProduct() {
        return smallestProduct;
    }

    public void setSmallestProduct(ProductEntity smallestProduct) {
        this.smallestProduct = smallestProduct;
    }
}