package com.proyect.moodle.AppClass.Docente;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.moodle.AppClass.Decano.crear_asignatura;
import com.proyect.moodle.AppClass.Decano.decano_gestion;
import com.proyect.moodle.AppClass.GlobalInfo;
import com.proyect.moodle.MainActivity;
import com.proyect.moodle.R;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class clase_gestion extends AppCompatActivity {
    ApiInterface API = ApiInterface.retrofit.create(ApiInterface.class);

    String ID_docente, cod_asignatura, ID_clase = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_gestion);

        TextView tv_nombre = findViewById(R.id.text_semana);
        TextView tv_hora = findViewById(R.id.text_materia);
        TextView tv_semana = findViewById(R.id.text_hora);

        String materiaNombre = getIntent().getExtras().getString("nombre");
        String horaValor = getIntent().getExtras().getString("hora");
        String semanaNombre = getIntent().getExtras().getString("dia");

        cod_asignatura = getIntent().getExtras().getString("cod_asignatura");
        ID_docente = GlobalInfo.ID_usuario;

        tv_nombre.setText(semanaNombre);
        tv_hora.setText(materiaNombre);
        tv_semana.setText(horaValor);
    }

    public void iniciar_clase(View view) {
        TextView tv_tema = findViewById(R.id.et_tema);
        String tema = tv_tema.getText().toString();

        if (!tema.equals("")) {
            //  asistencia profesor
            // 0 = inasistencia
            // 1 = asistencia

            // estado clase
            // 0 = ausenta
            // 1 = inicia
            // 2 = termina
            gestionar_clase(true,ID_docente,cod_asignatura,tema,"1","1");
        } else {
            Toast.makeText(getApplicationContext(),"Ingresé el tema de la clase", Toast.LENGTH_SHORT).show();
        }
    }
    public void ausentar_clase(View view) {
        TextView tv_motivo = findViewById(R.id.et_motivo);
        String motivo = tv_motivo.getText().toString();

        if (!motivo.equals("")) {
            //  asistencia profesor
            // 0 = inasistencia
            // 1 = asistencia

            // estado clase
            // 0 = ausenta
            // 1 = inicia
            // 2 = termina
            gestionar_clase(false,ID_docente,cod_asignatura,motivo,"0","0");
        } else {
            Toast.makeText(getApplicationContext(),"Ingresé el motivo de la ausencia", Toast.LENGTH_SHORT).show();
        }
    }

    public void gestionar_clase(final Boolean tipo, String ID_docente, String cod_asignatura, String descripcion, String asistencia_profesor, String estado) {
        Call<ResData> response = API.serGestionarClase(ID_docente, cod_asignatura, descripcion, asistencia_profesor, estado);
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
                            JSONObject json = new JSONObject(data);
                            ID_clase = json.getString("ID_clase");
                            Toast.makeText(clase_gestion.this, "Clase gestionada exitosamente", Toast.LENGTH_SHORT).show();
                            if (tipo) {
                                Intent i = new Intent(clase_gestion.this, clase_activa.class );

                                i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_docente"));
                                i.putExtra("cod_clase", ID_clase);

                                TextView tv_dia = findViewById(R.id.text_materia);
                                String dia = tv_dia.getText().toString();
                                i.putExtra("dia", dia);

                                TextView tv_hora = findViewById(R.id.text_hora);
                                String hora = tv_hora.getText().toString();
                                i.putExtra("hora", hora);

                                TextView tv_semana = findViewById(R.id.text_semana);
                                String semana = tv_semana.getText().toString();
                                i.putExtra("nombre", semana);

                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(),"Clase ausentada", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(clase_gestion.this, docente_gestion.class );
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(clase_gestion.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();
        startActivity(new Intent(clase_gestion.this, docente_gestion.class));
        finish();
    }
}
