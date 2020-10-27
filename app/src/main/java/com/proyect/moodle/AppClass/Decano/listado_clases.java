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
import com.proyect.moodle.clase_modelo;
import com.proyect.moodle.rv_listado_clases_adaptador;

import java.util.ArrayList;
import java.util.List;

public class listado_clases extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    private RecyclerView rv_listado_clases;
    private rv_listado_clases_adaptador listado_clases_adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_clases);

        rv_listado_clases = (RecyclerView)findViewById(R.id.rv_clases);
        rv_listado_clases.setLayoutManager(new LinearLayoutManager(this));

        listado_clases_adaptador = new rv_listado_clases_adaptador(obtener_clases());
        listado_clases_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_docentes().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
//                horario_docente(view);
            }
        });
        rv_listado_clases.setAdapter(listado_clases_adaptador);
    }

    public List<clase_modelo> obtener_clases() {
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor filas = bd.rawQuery("select a.nombre, b.cod_clase, b.descripcion, b.asistencia_profesor, c.nombre, c.n_creditos from Usuario a, Clase b, Asignatura c where a.ID_usuario=b.ID_docente and b.cod_asignatura=c.cod_asignatura;", null);
        List<clase_modelo> docentes = new ArrayList<>();
        if(filas.moveToFirst()) {
            do {
                String asistencia = "";
                if (filas.getString(3).equals("0")) {
                    asistencia = "No";
                } else if (filas.getString(3).equals("1")) {
                    asistencia = "Si";
                }
                docentes.add(new clase_modelo( "ID Clase: "+filas.getString(1),filas.getString(4),"Creditos: "+filas.getString(5),filas.getString(0),"Asistencia: "+asistencia,filas.getString(2)));
            } while (filas.moveToNext());
        }
        return docentes;
    }
}