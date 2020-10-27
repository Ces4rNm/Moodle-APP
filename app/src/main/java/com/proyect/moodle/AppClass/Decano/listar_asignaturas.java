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
import com.proyect.moodle.rv_listar_asignaturas_adaptador;

import java.util.ArrayList;
import java.util.List;

public class listar_asignaturas extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    private RecyclerView rv_listado_asignatura;
    private rv_listar_asignaturas_adaptador listado_asignaturas_adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_asignaturas);

        rv_listado_asignatura = (RecyclerView)findViewById(R.id.rv_asignaturas);
        rv_listado_asignatura.setLayoutManager(new LinearLayoutManager(this));

        listado_asignaturas_adaptador = new rv_listar_asignaturas_adaptador(obtener_asignaturas());
        listado_asignaturas_adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_semanas().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
//                definir_materias(view);
            }
        });
        rv_listado_asignatura.setAdapter(listado_asignaturas_adaptador);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(listar_asignaturas.this, decano_gestion.class));
        finish();

    }

    public List<materia_modelo> obtener_asignaturas() {
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor filas = bd.rawQuery("select cod_asignatura, nombre, n_creditos from Asignatura;", null);
        List<materia_modelo> asignaturas = new ArrayList<>();
        if(filas.moveToFirst()) {
            do {
                asignaturas.add(new materia_modelo(filas.getString(1),"Creditos: "+filas.getString(2), filas.getString(0)));
            } while (filas.moveToNext());
        }
        return asignaturas;
    }


}