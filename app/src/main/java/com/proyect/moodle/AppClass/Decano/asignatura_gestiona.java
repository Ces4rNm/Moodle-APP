package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.Models.materia_modelo;
import com.proyect.moodle.Adapters.rv_horario_docente_adaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class asignatura_gestiona extends AppCompatActivity {

    private RecyclerView rv_listado_horario;
    private rv_horario_docente_adaptador listado_horario_adaptador;
    List<materia_modelo> asignaturas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_gestiona);

        setTitle(getIntent().getExtras().getString("nombre")+" / Horarios ");

        rv_listado_horario = (RecyclerView)findViewById(R.id.rv_asignar_asignaturas);
        rv_listado_horario.setLayoutManager(new LinearLayoutManager(this));

        listado_horario_adaptador = new rv_horario_docente_adaptador(asignaturas);
        obtener_horarios();
        listado_horario_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_docentes().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
//                horario_docente(view);
            }
        });
        rv_listado_horario.setAdapter(listado_horario_adaptador);
    }

    public void obtener_horarios() {
        asignaturas.clear();
        listado_horario_adaptador.notifyDataSetChanged();

        ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);
        Call<ResData> response = API.serListadoAsignaturasDocente(getIntent().getExtras().getString("ID_usuario"));
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
                                    String dia_semana = oneObject.getString("dia_semana");
                                    String hora = oneObject.getString("hora");
                                    String nombre = oneObject.getString("nombre");
                                    asignaturas.add(new materia_modelo( nombre, dia_semana, hora));
                                } catch (JSONException e) {
                                    // Oops
                                }
                                if (i == (jArray.length()-1)) {
                                    listado_horario_adaptador.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(asignatura_gestiona.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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