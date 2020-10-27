package com.proyect.moodle.Retrofit.Interface;

import com.proyect.moodle.Retrofit.Model.validacionUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface servicesMoodle {
    @POST("moodle/Consultas/validacionUsuario.php")
    Call<List<validacionUsuario>> validacionUsuario();
}
