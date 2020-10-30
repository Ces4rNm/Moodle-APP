package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.Models.materia_modelo;
import com.proyect.moodle.Adapters.rv_listado_docentes_adaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class listado_docentes extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    private RecyclerView rv_listado_docentes;
    private rv_listado_docentes_adaptador listado_docentes_adaptador;
    List<materia_modelo> docentes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_docentes);

        rv_listado_docentes = (RecyclerView)findViewById(R.id.rv_docentes);
        rv_listado_docentes.setLayoutManager(new LinearLayoutManager(this));

        listado_docentes_adaptador = new rv_listado_docentes_adaptador(docentes);
        obtener_docentes();
        listado_docentes_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_docentes().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                horario_docente(view);
            }
        });
        rv_listado_docentes.setAdapter(listado_docentes_adaptador);
    }


    public void obtener_docentes() {
        docentes.clear();
        listado_docentes_adaptador.notifyDataSetChanged();

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
                                    docentes.add(new materia_modelo( nombre, facultad,ID_usuario));
                                } catch (JSONException e) {
                                    // Oops
                                }
                                if (i == (jArray.length()-1)) {
                                    listado_docentes_adaptador.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(listado_docentes.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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

    public void horario_docente(View view) {
        Intent i = new Intent(this, asignatura_gestiona.class );
        i.putExtra("ID_usuario", docentes.get(rv_listado_docentes.getChildAdapterPosition(view)).getHora());
        i.putExtra("nombre", docentes.get(rv_listado_docentes.getChildAdapterPosition(view)).getNombre());
        i.putExtra("facultad", docentes.get(rv_listado_docentes.getChildAdapterPosition(view)).getSalon());
        startActivity(i);
    }
}