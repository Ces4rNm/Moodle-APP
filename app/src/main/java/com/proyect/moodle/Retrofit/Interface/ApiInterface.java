package com.proyect.moodle.Retrofit.Interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proyect.moodle.Retrofit.Model.ResData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.2/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @POST("moodle/Consultas/validacionUsuario.php")
    @FormUrlEncoded
    Call<ResData> serLogin(@Field("correo") String correo, @Field("password") String password);
}
