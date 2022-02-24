package com.example.service;

import com.squareup.okhttp.Response;
import com.teniapp.tenishopping.activity.storecreation.ResponseData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface RetrofitService {

    @GET
    Observable<ResponseData> getShopTypes(@Url String url, @Header("token") String token);

    @POST
    Observable<Response> updateProfile(@Url String url, @Header("token") String token, @Body Map<String, Object> data);

}
