package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;
import com.proyect.moodle.SQLite.Models.materia_modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class crear_horario extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    Spinner docente, asignatura, semestre, dia, hora;
    String salon;
    ArrayList<String> listaDocentes, listaAsignaturas, listaSemestre, listaDia, listaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_horario);

        // Cargando Spinner docentes
        docente = findViewById(R.id.spinner_docente);

        listaDocentes = new ArrayList<>();
        listaDocentes.add("Seleccionar Docente");

        Call<ResData> response = API.serListadoDocentes();
        response.enqueue(new Callback<ResData>() {
            @Override
            public void onResponse(Call<ResData> call, Response<ResData> response) {
                if(response.isSuccessful()) {
                    //Log.e("TAG", "response: "+new Gson().toJson(response.body()));
                    Log.d("Log",response.raw().toString());
                    Log.d("Log JSON",response.body().toString());
                    if (response.body().getCode().equals("0")) {
                        String data = response.body().getData();
                        try {
                            JSONArray jArray = new JSONArray(data);
                            for (int i=0; i < jArray.length(); i++) {
                                try {
                                    JSONObject oneObject = jArray.getJSONObject(i);
                                    // Pulling items from the array
                                    String ID_usuario = oneObject.getString("ID_usuario");
                                    String nombre = oneObject.getString("nombre");
                                    String facultad = oneObject.getString("facultad");
                                    listaDocentes.add(ID_usuario+" - "+nombre);
                                } catch (JSONException e) {
                                    // Oops
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(crear_horario.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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

        ArrayAdapter<CharSequence> adaptadorDocentes = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, listaDocentes);
        docente.setAdapter(adaptadorDocentes);
        // Cargando Spinner asignatura
        asignatura = findViewById(R.id.spinner_asignatura);

        listaAsignaturas = new ArrayList<>();
        listaAsignaturas.add("Seleccionar Asignatura");

        Call<ResData> response2 = API.serListadoAsignaturas();
        response2.enqueue(new Callback<ResData>() {
            @Override
            public void onResponse(Call<ResData> call, Response<ResData> response) {
                if(response.isSuccessful()) {
                    //Log.e("TAG", "response: "+new Gson().toJson(response.body()));
                    Log.d("Log",response.raw().toString());
                    Log.d("Log JSON",response.body().toString());
                    if (response.body().getCode().equals("0")) {
                        String data = response.body().getData();
                        try {
                            JSONArray jArray = new JSONArray(data);
                            for (int i=0; i < jArray.length(); i++) {
                                try {
                                    JSONObject oneObject = jArray.getJSONObject(i);
                                    // Pulling items from the array
                                    String cod_asignatura = oneObject.getString("cod_asignatura");
                                    String nombre = oneObject.getString("nombre");
                                    listaAsignaturas.add(cod_asignatura+" - "+nombre);
                                } catch (JSONException e) {
                                    // Oops
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(crear_horario.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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

        ArrayAdapter<CharSequence> adaptadorAsignaturas = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaAsignaturas);
        asignatura.setAdapter(adaptadorAsignaturas);
        // Cargando Spinner semestre
        semestre = findViewById(R.id.spinner_semestre);

        listaSemestre = new ArrayList<>();
        listaSemestre.add("Seleccionar Semestre");
        listaSemestre.add("2020 - 1");
        listaSemestre.add("2020 - 2");
        listaSemestre.add("2021 - 1");
        listaSemestre.add("2021 - 2");

        ArrayAdapter<CharSequence> adaptadorSemestre = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaSemestre);
        semestre.setAdapter(adaptadorSemestre);
        // Cargando Spinner dia
        dia = findViewById(R.id.spinner_dia);

        listaDia = new ArrayList<>();
        listaDia.add("Seleccionar Dia");
        listaDia.add("Lunes");
        listaDia.add("Martes");
        listaDia.add("Miercoles");
        listaDia.add("Jueves");
        listaDia.add("Viernes");
        listaDia.add("Sabado");
        listaDia.add("Domingo");

        ArrayAdapter<CharSequence> adaptadorDia = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaDia);
        dia.setAdapter(adaptadorDia);
        // Cargando Spinner hora
        hora = findViewById(R.id.spinner_hora);

        listaHora = new ArrayList<>();
        listaHora.add("Seleccionar Hora");
        listaHora.add("7:00 - 8:00");
        listaHora.add("8:00 - 9:00");
        listaHora.add("9:00 - 10:00");
        listaHora.add("10:00 - 11:00");
        listaHora.add("11:00 - 12:00");

        ArrayAdapter<CharSequence> adaptadorHora = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaHora);
        hora.setAdapter(adaptadorHora);
    }

    public void crear_horario(View view){

        String selectDocente = docente.getSelectedItem().toString();
        String[] codigo2 = selectDocente.split(" - ");
        String ID_docente = codigo2[0];

        String selectAsignatura = asignatura.getSelectedItem().toString();
        String[] codigo = selectAsignatura.split(" - ");
        String codigo1 = codigo[0];

        String selectSemestre = semestre.getSelectedItem().toString();
        String selectDia = dia.getSelectedItem().toString();
        String selectHora = hora.getSelectedItem().toString();
        EditText et_salon = findViewById(R.id.et_salon);
        String salon = et_salon.getText().toString();

        if (selectAsignatura.equals("Seleccionar Asignatura") || selectSemestre.equals("Seleccionar Semestre")|| selectDia.equals("Seleccionar Dia")|| selectHora.equals("Seleccionar Hora") || salon.equals("")) {
            Toast.makeText(this, "Complete los campos para crear una asignatura", Toast.LENGTH_SHORT).show();
        } else {
            Call<ResData> response3 = API.serMatricular(ID_docente, codigo1, selectSemestre, selectDia, selectHora, salon);
            response3.enqueue(new Callback<ResData>() {
                @Override
                public void onResponse(Call<ResData> call, Response<ResData> response) {
                    if(response.isSuccessful()) {
                        //Log.e("TAG", "response: "+new Gson().toJson(response.body()));
                        Log.d("Log",response.raw().toString());
                        Log.d("Log JSON",response.body().toString());
                        if (response.body().getCode().equals("0")) {
                            Toast.makeText(crear_horario.this, "Horario creado con exito", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(crear_horario.this, decano_gestion.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(crear_horario.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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