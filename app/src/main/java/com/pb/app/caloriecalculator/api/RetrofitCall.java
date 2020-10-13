package com.pb.app.caloriecalculator.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pb.app.caloriecalculator.entity.AuthorisationEntity;
import com.pb.app.caloriecalculator.entity.ProductEntity;
import com.pb.app.caloriecalculator.entity.ProductsEntity;
import com.pb.app.caloriecalculator.entity.StatisticEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCall {

    private static RetrofitCall instance;

    private MutableLiveData<ProductsEntity> productsLiveData = new MutableLiveData<>();
    private MutableLiveData<StatisticEntity> statisticLiveData = new MutableLiveData<>();

    private ProductsEntity productsEntity;
    private AuthorisationEntity authorisationEntity;
    private StatisticEntity statisticEntity;

    public static RetrofitCall getInstance(){
        if(instance == null) instance = new RetrofitCall();
        return instance;
    }

    public void callProducts(){
        App.getInstance().getApi().getProducts("Bearer " + authorisationEntity.getToken(), DateResponse.getInstance().getDateProducts()).enqueue(new Callback<ProductsEntity>() {
            @Override
            public void onResponse(Call<ProductsEntity> call, Response<ProductsEntity> response) {
                if (response.code() == 200){
                    productsEntity = new ProductsEntity();
                    if (response.body() == null){
                        productsEntity.setProducts(new ArrayList<ProductEntity>());
                    }
                    else {
                        productsEntity.setProducts(response.body().getProducts());
                    }
                    productsLiveData.setValue(productsEntity);
//                    productsLiveData.postValue(productsEntity);
                    Log.d("API", "продукты получены " + response.code() + " || " + response.body().toString() + " || " + response.message() + " || " + DateResponse.getInstance().getDateProducts());
                } else {
                    Log.d("API", "продукты не получены" + response.message() + " || код " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ProductsEntity> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(call.toString());
            }
        });
    }

    public void deleteProduct(int id){
        App.getInstance().getApi().delProduct("Bearer " + authorisationEntity.getToken(), id).enqueue(new Callback<ProductEntity>() {
            @Override
            public void onResponse(Call<ProductEntity> call, Response<ProductEntity> response) {
                callProducts();
                if (response.code() == 202){
                    Log.d("API", "продукт удален " + response.code() + " || " + response.body() + " || " + response.message());
                } else {
                    Log.d("API", "продукт не удален" + response.message() + " || код " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ProductEntity> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void addProduct(ProductEntity product){
        App.getInstance().getApi().addProduct("Bearer " + authorisationEntity.getToken(), product.getName(), product.getProductNum(), product.getCalorieNum(), product.getCountingType()).enqueue(new Callback<ProductEntity>() {
            @Override
            public void onResponse(Call<ProductEntity> call, Response<ProductEntity> response) {
                if (response.code() == 201){
                    callProducts();
                    Log.d("API", "продукт добавлен " + response.code() + " || " + response.body() + " || " + response.message());
                } else {
                    Log.d("API", "продукт не добавлен" + response.message() + " || код " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ProductEntity> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void editProduct(ProductEntity product){
        App.getInstance().getApi().setProduct("Bearer " + authorisationEntity.getToken(), product.getId(), product.getName(), product.getProductNum(), product.getCalorieNum(), product.getCountingType()).enqueue(new Callback<ProductEntity>() {
            @Override
            public void onResponse(Call<ProductEntity> call, Response<ProductEntity> response) {
                callProducts();
                if (response.code() == 201){

                    Log.d("API", "продукт добавлен " + response.code() + " || " + response.body() + " || " + response.message());
                } else {
                    Log.d("API", "продукт не добавлен" + response.message() + " || код " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ProductEntity> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void statisticLoad(){
        App.getInstance().getApi().getStatistic("Bearer " + authorisationEntity.getToken(),
                DateResponse.getInstance().getDatesStatistic().get(0),
                DateResponse.getInstance().getDatesStatistic().get(1)).enqueue(new Callback<StatisticEntity>() {
            @Override
            public void onResponse(Call<StatisticEntity> call, Response<StatisticEntity> response) {
                if (response.code() == 200){
                    if (response.body() == null){
                        statisticEntity = new StatisticEntity();
                    }
                    else {
                        statisticEntity = response.body();
                    }
                    statisticLiveData.setValue(statisticEntity);
                    Log.d("API", "статистика получена " + response.code() + " || " + response.body().toString() + " || " + response.message() + " || " + DateResponse.getInstance().getDateProducts());
                } else {
                    Log.d("API", "статистика не получена " + response.message() + " || код " + response.code());
                }

            }

            @Override
            public void onFailure(Call<StatisticEntity> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(call.toString());
            }
        });
    }

    public AuthorisationEntity getAuthorisationEntity() {
        return authorisationEntity;
    }

    public void setAuthorisationEntity(AuthorisationEntity auth){
        authorisationEntity = auth;
    }

    public ProductsEntity getProductsEntity() {
        return productsEntity;
    }

    public StatisticEntity getStatisticEntity() {
        return statisticEntity;
    }

    public LiveData<ProductsEntity> getProductsEntityState(){
        return productsLiveData;
    }

    public LiveData<StatisticEntity> getStatisticEntityState(){
        return statisticLiveData;
    }
}
