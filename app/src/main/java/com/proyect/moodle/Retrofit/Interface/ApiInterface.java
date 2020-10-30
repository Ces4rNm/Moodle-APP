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
import retrofit2.http.GET;
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

    @POST("moodle/Consultas/insertarUsuario.php")
    @FormUrlEncoded
    Call<ResData> serCrearUsuario(@Field("ID_usuario") String ID_usuario, @Field("nombre") String nombre, @Field("edad") String edad, @Field("sexo") String sexo, @Field("estudios") String estudios, @Field("correo") String correo, @Field("password") String password, @Field("rol") String rol, @Field("facultad") String facultad);

    @GET("moodle/Consultas/mostrarDocentes.php")
    Call<ResData> serListadoDocentes();

    @POST("moodle/Consultas/mostrarAsignaturasDocente.php")
    @FormUrlEncoded
    Call<ResData> serListadoAsignaturasDocente(@Field("ID_usuario") String ID_usuario);

    @GET("moodle/Consultas/mostrarAsignaturas.php")
    Call<ResData> serListadoAsignaturas();

    @POST("moodle/Consultas/crearHorario.php")
    @FormUrlEncoded
    Call<ResData> serMatricular(@Field("ID_docente") String ID_docente, @Field("cod_asignatura") String cod_asignatura, @Field("semestre") String semestre, @Field("dia_semana") String dia_semana, @Field("hora") String hora, @Field("salon") String salon);

    @POST("moodle/Consultas/CrearAsignatura.php")
    @FormUrlEncoded
    Call<ResData> serCrearAsignatura(@Field("nombre") String nombre, @Field("n_creditos") String n_creditos);

    @GET("moodle/Consultas/listarClases.php")
    Call<ResData> serListadoClases();
}
