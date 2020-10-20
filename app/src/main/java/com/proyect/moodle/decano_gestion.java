package com.proyect.moodle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class decano_gestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decano_gestion);
    }

    public void crear_docente(View view) {
        Intent i = new Intent(this, crear_docente.class );
        startActivity(i);
    }

    public void listar_docentes(View view) {
        Intent i = new Intent(this, listado_docentes.class );
        startActivity(i);
    }

    public void crear_asignatura(View view) {
        Intent i = new Intent(this, crear_asignatura.class );
        startActivity(i);
    }

    public void listar_asignatura(View view) {
        Intent i = new Intent(this, listar_asignaturas.class );
        startActivity(i);
    }

    public void listar_clases(View view) {
        Intent i = new Intent(this, listado_clases.class );
        startActivity(i);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(decano_gestion.this, MainActivity.class));
        finish();

    }
}
