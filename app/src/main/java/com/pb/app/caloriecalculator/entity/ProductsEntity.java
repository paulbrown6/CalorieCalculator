package com.pb.app.caloriecalculator.entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsEntity {

    @SerializedName("data")
    @Expose
    private List<ProductEntity> data;
    @SerializedName("count")
    @Expose
    private int count;

    public List<ProductEntity> getProducts() {
        return data;
    }

    public void setProducts(List<ProductEntity> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}