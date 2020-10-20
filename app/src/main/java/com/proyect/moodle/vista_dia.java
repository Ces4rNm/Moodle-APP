package com.proyect.moodle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class vista_dia extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
    String dia = "", semestre = "2020 - 1";

    public void ver_semana(View view) {
        Intent i = new Intent(this, vista_semana.class );
        i.putExtra("validadorSemestre", "0");
        i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
        i.putExtra("semestre", semestre);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(vista_dia.this, MainActivity.class));
        finish();

    }

    public void gestionar_clase(View view) {
        Intent i = new Intent(this, clase_gestion.class );

        String nombre = obtener_materias().get(rv_vista_materias.getChildAdapterPosition(view)).getNombre();
        String salon1 = obtener_materias().get(rv_vista_materias.getChildAdapterPosition(view)).getSalon();

        String[] salon = salon1.split("ID: ");
        String cod_asignatura = salon[1];

        String hora = obtener_materias().get(rv_vista_materias.getChildAdapterPosition(view)).getHora();
        i.putExtra("nombre", nombre);
        i.putExtra("hora", hora);
        i.putExtra("cod_asignatura", cod_asignatura);
        i.putExtra("ID_docente", getIntent().getExtras().getString("ID_usuario"));

        TextView tv_dia = findViewById(R.id.text_semana);
        String dia = tv_dia.getText().toString();
        i.putExtra("dia", dia);

        startActivity(i);
    }

    private RecyclerView rv_vista_materias;
    private rv_materias_adaptador adaptador_materias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_dia);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String validadorSemana = getIntent().getExtras().getString("validadorSemana");


        TextView tv_dia = findViewById(R.id.text_semana);

        if (validadorSemana.equals("0")) {
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
            tv_dia.setText(dia);
        } else {
            String diaNombre = getIntent().getExtras().getString("dia");
            tv_dia.setText(diaNombre);

            dia = diaNombre;
            semestre = getIntent().getExtras().getString("semestre");
        }

        rv_vista_materias = (RecyclerView)findViewById(R.id.rv_semestre);
        rv_vista_materias.setLayoutManager(new LinearLayoutManager(this));

        adaptador_materias = new rv_materias_adaptador(obtener_materias());
        adaptador_materias.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_materias().get(rv_vista_materias.getChildAdapterPosition(view)).getSalon(), Toast.LENGTH_SHORT).show();
                gestionar_clase(view);
            }
        });
        rv_vista_materias.setAdapter(adaptador_materias);
    }

    public List<materia_modelo> obtener_materias() {
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor filas = bd.rawQuery("select b.hora, b.salon, c.nombre, c.cod_asignatura " +
                "from Usuario a, Horario b, Asignatura c " +
                "where a.ID_usuario=b.ID_docente and b.cod_asignatura=c.cod_asignatura and a.ID_usuario="+getIntent().getExtras().getString("ID_usuario")+" and b.dia_semana='"+dia+"' and b.semestre='"+semestre+"';", null);
        List<materia_modelo> materia = new ArrayList<>();
        if(filas.moveToFirst()) {
            do {
                materia.add(new materia_modelo( filas.getString(2),"Salon: "+filas.getString(1)+"      ID: "+filas.getString(3),filas.getString(0)));
            } while (filas.moveToNext());
        }else {
            Toast.makeText(getApplicationContext(),"No tiene clases matriculadas hoy: "+dia+" ("+semestre+")", Toast.LENGTH_SHORT).show();
        }
        return materia;
    }
}
