package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;
import com.proyect.moodle.SQLite.Models.materia_modelo;
import com.proyect.moodle.Adapters.rv_listar_asignaturas_adaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class listar_asignaturas extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    private RecyclerView rv_listado_asignatura;
    private rv_listar_asignaturas_adaptador listado_asignaturas_adaptador;
    List<materia_modelo> asignaturas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_asignaturas);

        rv_listado_asignatura = (RecyclerView)findViewById(R.id.rv_asignaturas);
        rv_listado_asignatura.setLayoutManager(new LinearLayoutManager(this));

        listado_asignaturas_adaptador = new rv_listar_asignaturas_adaptador(asignaturas);
        obtener_asignaturas();
        listado_asignaturas_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_semanas().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
//                definir_materias(view);
            }
        });
        rv_listado_asignatura.setAdapter(listado_asignaturas_adaptador);
    }

    public void obtener_asignaturas() {
        asignaturas.clear();
        listado_asignaturas_adaptador.notifyDataSetChanged();

        Call<ResData> response = API.serListadoAsignaturas();
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
                                    String cod_asignatura = oneObject.getString("cod_asignatura");
                                    String nombre = oneObject.getString("nombre");
                                    String n_creditos = oneObject.getString("n_creditos");
                                    asignaturas.add(new materia_modelo( nombre, "Creditos: "+nombre, cod_asignatura));
                                } catch (JSONException e) {
                                    // Oops
                                }
                                if (i == (jArray.length()-1)) {
                                    listado_asignaturas_adaptador.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(listar_asignaturas.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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