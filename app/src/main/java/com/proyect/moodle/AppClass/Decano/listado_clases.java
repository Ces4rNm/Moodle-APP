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

import com.proyect.moodle.AppClass.GlobalInfo;
import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;
import com.proyect.moodle.SQLite.Models.clase_modelo;
import com.proyect.moodle.Adapters.rv_listado_clases_adaptador;
import com.proyect.moodle.SQLite.Models.materia_modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class listado_clases extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    private RecyclerView rv_listado_clases;
    private rv_listado_clases_adaptador listado_clases_adaptador;
    List<clase_modelo> clases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_clases);

        rv_listado_clases = (RecyclerView)findViewById(R.id.rv_clases);
        rv_listado_clases.setLayoutManager(new LinearLayoutManager(this));

        listado_clases_adaptador = new rv_listado_clases_adaptador(clases);
        obtener_clases();
        listado_clases_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_docentes().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
//                horario_docente(view);
            }
        });
        rv_listado_clases.setAdapter(listado_clases_adaptador);
    }

    public void obtener_clases() {
        clases.clear();
        listado_clases_adaptador.notifyDataSetChanged();
        Call<ResData> response;
        if (GlobalInfo.Rol.equals("2")) {
            response = API.serListadoClasesDocente(GlobalInfo.ID_usuario);
        } else {
            response = API.serListadoClases();
        }
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
                                    String nombre = oneObject.getString("nombre");
                                    String cod_clase = oneObject.getString("cod_clase");
                                    String descripcion = oneObject.getString("descripcion");
                                    String asistencia_profesor = oneObject.getString("asistencia_profesor");
                                    String asignatura = oneObject.getString("asignatura");
                                    String n_creditos = oneObject.getString("n_creditos");

                                    String asistencia = "";
                                    if (asistencia_profesor.equals("0")) {
                                        asistencia = "No";
                                    } else if (asistencia_profesor.equals("1")) {
                                        asistencia = "Si";
                                    }

                                    clases.add(new clase_modelo( "ID Clase: "+cod_clase, asignatura,"Creditos: "+n_creditos, nombre,"Asistencia: "+asistencia, descripcion));
                                } catch (JSONException e) {
                                    // Oops
                                }
                                if (i == (jArray.length()-1)) {
                                    listado_clases_adaptador.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(listado_clases.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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