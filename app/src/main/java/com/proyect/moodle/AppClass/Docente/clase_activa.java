package com.proyect.moodle.AppClass.Docente;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.moodle.AppClass.Decano.crear_asignatura;
import com.proyect.moodle.AppClass.GlobalInfo;
import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class clase_activa extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_activa);

        TextView tv_nombre = findViewById(R.id.text_materia);
        TextView tv_hora = findViewById(R.id.text_hora);
        TextView tv_semana = findViewById(R.id.text_semana);

        String materiaNombre = getIntent().getExtras().getString("nombre");
        String horaValor = getIntent().getExtras().getString("hora");
        String semanaNombre = getIntent().getExtras().getString("dia");

        tv_nombre.setText(materiaNombre);
        tv_hora.setText(horaValor);
        tv_semana.setText(semanaNombre);
    }

    public void terminar_clase(View view) {
        String cod_clase = getIntent().getExtras().getString("cod_clase");
        Call<ResData> response = API.serActualizarClase(cod_clase, "2");
        response.enqueue(new Callback<ResData>() {
            @Override
            public void onResponse(Call<ResData> call, Response<ResData> response) {
                if(response.isSuccessful()) {
                    //Log.e("TAG", "response: "+new Gson().toJson(response.body()));
                    Log.d("Log",response.raw().toString());
                    Log.d("Log JSON",response.body().toString());
                    if (response.body().getCode().equals("0")) {
                        Toast.makeText(getApplicationContext(), "Clase finalizada", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(clase_activa.this, docente_gestion.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(clase_activa.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "La clase debe ser finalizada", Toast.LENGTH_SHORT).show();
    }
}

