package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;
import com.proyect.moodle.SQLite.Models.materia_modelo;
import com.proyect.moodle.Adapters.rv_horario_docente_adaptador;

import java.util.ArrayList;
import java.util.List;

public class asignatura_gestiona extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    private RecyclerView rv_listado_horario;
    private rv_horario_docente_adaptador listado_horario_adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_gestiona);

        setTitle(getIntent().getExtras().getString("nombre")+" / Horarios ");

        rv_listado_horario = (RecyclerView)findViewById(R.id.rv_asignar_asignaturas);
        rv_listado_horario.setLayoutManager(new LinearLayoutManager(this));

        listado_horario_adaptador = new rv_horario_docente_adaptador(obtener_horarios());
        listado_horario_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_docentes().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
//                horario_docente(view);
            }
        });
        rv_listado_horario.setAdapter(listado_horario_adaptador);
    }

    public List<materia_modelo> obtener_horarios() {
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor filas = bd.rawQuery("select b.dia_semana, b.hora, c.nombre " +
                "from Usuario a, Horario b, Asignatura c " +
                "where a.ID_usuario=b.ID_docente and b.cod_asignatura=c.cod_asignatura and a.ID_usuario="+getIntent().getExtras().getString("ID_usuario")+";", null);
        List<materia_modelo> docentes = new ArrayList<>();
        if(filas.moveToFirst()) {
            do {
                docentes.add(new materia_modelo( filas.getString(2),filas.getString(0),filas.getString(1)));
            } while (filas.moveToNext());
        }
        return docentes;
    }
}