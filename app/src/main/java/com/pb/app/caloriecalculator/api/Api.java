package com.pb.app.caloriecalculator.api;

import com.pb.app.caloriecalculator.entity.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("signin")
    Call<AuthorisationEntity> getSignin(@Query("email") String email, @Query("password") String password);

    @POST("adduser")
    Call<RegistrationEntity> getRegistration(@Query("name") String name, @Query("email") String email, @Query("password") String password);

    @GET("products")
    Call<ProductsEntity> getProducts(@Header("Authorization") String token, @Query("date") String date);

    @POST("products")
    Call<ProductEntity> addProduct(@Header("Authorization") String token, @Query("name") String name, @Query("product_num") int productNum, @Query("calorie_num") int calorieNum, @Query("counting_type") int countingType);

    @PATCH("products/{id}")
    Call<ProductEntity> setProduct(@Header("Authorization") String token, @Path("id") int id, @Query("name") String name, @Query("product_num") int productNum, @Query("calorie_num") int calorieNum, @Query("counting_type") int countingType);

    @DELETE("products/{id}")
    Call<ProductEntity> delProduct(@Header("Authorization") String token, @Path("id") int id);

    @GET("products")
    Call<StatisticEntity> getStatistic(@Header("Authorization") String token, @Query("start_date") String startDate, @Query("end_date") String endDate);
}
