package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;

public class crear_asignatura extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    EditText et_nombre_asignatura, et_credito_asignatura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_asignatura);

        et_nombre_asignatura = (EditText)findViewById(R.id.et_nombre_asignatura);
        et_credito_asignatura = (EditText)findViewById(R.id.et_credito_asignatura);
    }

    public void crearAsignatura(View view) {

        String nombre = et_nombre_asignatura.getText().toString();
        String n_creditos = et_credito_asignatura.getText().toString();

        if (nombre.equals("") || n_creditos.equals("")) {
            Toast.makeText(this, "Complete el formulario de la asignatura", Toast.LENGTH_SHORT).show();
        } else {
            Call<ResData> response = API.serCrearAsignatura(nombre, n_creditos);
            response.enqueue(new Callback<ResData>() {
                @Override
                public void onResponse(Call<ResData> call, Response<ResData> response) {
                    if(response.isSuccessful()) {
                        //Log.e("TAG", "response: "+new Gson().toJson(response.body()));
                        Log.d("Log",response.raw().toString());
                        Log.d("Log JSON",response.body().toString());
                        if (response.body().getCode().equals("0")) {
                            et_nombre_asignatura.setText("");
                            et_credito_asignatura.setText("");

                            Toast.makeText(crear_asignatura.this, "Asignatura creada con exito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(crear_asignatura.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("notSuccessful", String.valueOf(response));
                    }
                }
                @Override
                public void onFailure(Call<ResData> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }
    }
}