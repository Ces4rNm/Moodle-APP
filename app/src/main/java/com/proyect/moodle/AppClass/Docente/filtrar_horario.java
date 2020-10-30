package com.proyect.moodle.AppClass.Docente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.proyect.moodle.AppClass.GlobalInfo;
import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;
import com.proyect.moodle.SQLite.Models.materia_modelo;
import com.proyect.moodle.Adapters.rv_materias_adaptador;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class filtrar_horario extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    Calendar mClndr;
    DatePickerDialog dpd;
    String fecha;
    String dia = "", semestre = "";

    private RecyclerView rv_vista_materias;
    private rv_materias_adaptador adaptador_materias;
    List<materia_modelo> materia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_horario);

        final EditText etFecha = findViewById(R.id.etFecha);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                mClndr = Calendar.getInstance();
                int day = mClndr.get(Calendar.DAY_OF_MONTH);
                int month = mClndr.get(Calendar.MONTH);
                int year = mClndr.get(Calendar.YEAR);

                dpd = new DatePickerDialog(filtrar_horario.this, R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        fecha = mYear + "-" + (mMonth+1) + "-" + mDay;
                        etFecha.setText(fecha);
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    public void listar_filtro(View view) {
        Calendar calendar = Calendar.getInstance();

        String string = fecha;
        String[] parts = string.split("-");
        calendar.set(Integer.parseInt(parts[0]), (Integer.parseInt(parts[1]) - 1), Integer.parseInt(parts[2]));

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
                        Toast.makeText(filtrar_horario.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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

        EditText tv_dia = findViewById(R.id.etFecha);
        String dia = tv_dia.getText().toString();
        i.putExtra("dia", dia);

        startActivity(i);
    }
}