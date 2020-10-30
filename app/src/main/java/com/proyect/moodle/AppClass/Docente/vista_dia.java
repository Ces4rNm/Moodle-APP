package com.proyect.moodle.AppClass.Docente;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.moodle.AppClass.Decano.listado_docentes;
import com.proyect.moodle.AppClass.GlobalInfo;
import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;
import com.proyect.moodle.SQLite.Models.materia_modelo;
import com.proyect.moodle.Adapters.rv_materias_adaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class vista_dia extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    String dia = "", semestre = "";
    private RecyclerView rv_vista_materias;
    private rv_materias_adaptador adaptador_materias;
    List<materia_modelo> materia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_dia);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int periodo = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        switch (day) {
            case Calendar.SUNDAY:
                dia = "Domingo";
                break;
            case Calendar.MONDAY:
                dia = "Lunes";
                break;
            case Calendar.TUESDAY:
                dia = "Martes";
                break;
            case Calendar.WEDNESDAY:
                dia = "Miercoles";
                break;
            case Calendar.THURSDAY:
                dia = "Jueves";
                break;
            case Calendar.FRIDAY:
                dia = "Viernes";
                break;
            case Calendar.SATURDAY:
                dia = "Sabado";
                break;
        }
        if (periodo <= 5) {
            semestre = year+"-1";
        } else {
            semestre = year+"-2";
        }

        TextView tv_dia = findViewById(R.id.text_semana);
        tv_dia.setText(dia+" / "+semestre);

        rv_vista_materias = (RecyclerView)findViewById(R.id.rv_semestre);
        rv_vista_materias.setLayoutManager(new LinearLayoutManager(this));

        adaptador_materias = new rv_materias_adaptador(materia);
        obtener_materias();
        adaptador_materias.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_materias().get(rv_vista_materias.getChildAdapterPosition(view)).getSalon(), Toast.LENGTH_SHORT).show();
                gestionar_clase(view);
            }
        });
        rv_vista_materias.setAdapter(adaptador_materias);
    }

    public void obtener_materias() {
        materia.clear();
        adaptador_materias.notifyDataSetChanged();

        Call<ResData> response = API.serListadoHorariosDocente(GlobalInfo.ID_usuario, dia, semestre);
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
                                    String hora = oneObject.getString("hora");
                                    String salon = oneObject.getString("salon");
                                    String nombre = oneObject.getString("nombre");
                                    String cod_asignatura = oneObject.getString("cod_asignatura");
                                    materia.add(new materia_modelo(  nombre,"Salon: "+salon+"      ID: "+cod_asignatura, hora));
                                } catch (JSONException e) {
                                    // Oops
                                }
                                if (i == (jArray.length()-1)) {
                                    adaptador_materias.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(vista_dia.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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

    public void gestionar_clase(View view) {
        Intent i = new Intent(this, clase_gestion.class );

        String nombre = materia.get(rv_vista_materias.getChildAdapterPosition(view)).getNombre();
        String salon1 = materia.get(rv_vista_materias.getChildAdapterPosition(view)).getSalon();

        String[] salon = salon1.split("ID: ");
        String cod_asignatura = salon[1];

        String hora = materia.get(rv_vista_materias.getChildAdapterPosition(view)).getHora();
        i.putExtra("nombre", nombre);
        i.putExtra("hora", hora);
        i.putExtra("cod_asignatura", cod_asignatura);
        i.putExtra("ID_docente", GlobalInfo.ID_usuario);

        TextView tv_dia = findViewById(R.id.text_semana);
        String dia = tv_dia.getText().toString();
        i.putExtra("dia", dia);

        startActivity(i);
    }
}
