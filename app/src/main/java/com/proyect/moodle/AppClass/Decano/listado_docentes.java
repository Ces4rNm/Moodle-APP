package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;
import com.proyect.moodle.materia_modelo;
import com.proyect.moodle.rv_listado_docentes_adaptador;

import java.util.ArrayList;
import java.util.List;

public class listado_docentes extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    public void horario_docente(View view) {
        Intent i = new Intent(this, asignatura_gestiona.class );
        i.putExtra("ID_usuario", obtener_docentes().get(rv_listado_docentes.getChildAdapterPosition(view)).getHora());
        i.putExtra("nombre", obtener_docentes().get(rv_listado_docentes.getChildAdapterPosition(view)).getNombre());
        i.putExtra("facultad", obtener_docentes().get(rv_listado_docentes.getChildAdapterPosition(view)).getSalon());
        startActivity(i);
    }

    private RecyclerView rv_listado_docentes;
    private rv_listado_docentes_adaptador listado_docentes_adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_docentes);

        rv_listado_docentes = (RecyclerView)findViewById(R.id.rv_docentes);
        rv_listado_docentes.setLayoutManager(new LinearLayoutManager(this));

        listado_docentes_adaptador = new rv_listado_docentes_adaptador(obtener_docentes());
        listado_docentes_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_docentes().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                horario_docente(view);
            }
        });
        rv_listado_docentes.setAdapter(listado_docentes_adaptador);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(listado_docentes.this, decano_gestion.class));
        finish();

    }

    public List<materia_modelo> obtener_docentes() {
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor filas = bd.rawQuery("select ID_usuario, nombre, facultad from Usuario where rol='2';", null);
        List<materia_modelo> docentes = new ArrayList<>();
        if(filas.moveToFirst()) {
            do {
                docentes.add(new materia_modelo( filas.getString(1),filas.getString(2),filas.getString(0)));
            } while (filas.moveToNext());
        }
        return docentes;
    }
}