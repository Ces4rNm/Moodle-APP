package com.proyect.moodle.retrofit.Interface;

import com.proyect.moodle.retrofit.Model.validacionUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface servicesMoodle {
    @POST("moodle/Consultas/validacionUsuario.php")
    Call<List<validacionUsuario>> validacionUsuario();
}
