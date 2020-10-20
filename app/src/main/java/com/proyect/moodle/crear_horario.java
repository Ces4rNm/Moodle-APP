package com.proyect.moodle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class crear_horario extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    Spinner asignatura, semestre, dia, hora;
    String salon;
    ArrayList<String> listaAsignaturas, listaSemestre, listaDia, listaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_horario);

//      Cargando Spinner asignatura
        asignatura = findViewById(R.id.spinner_asignatura);

        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor filas = bd.rawQuery("select cod_asignatura, nombre from Asignatura;", null);

        listaAsignaturas = new ArrayList<>();
        listaAsignaturas.add("Seleccionar Asignatura");

        if(filas.moveToFirst()) {
            do {
                listaAsignaturas.add(filas.getString(0)+" - "+filas.getString(1));
            } while (filas.moveToNext());
        }

        ArrayAdapter<CharSequence> adaptadorAsignaturas = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaAsignaturas);
        asignatura.setAdapter(adaptadorAsignaturas);
//      Cargando Spinner semestre
        semestre = findViewById(R.id.spinner_semestre);

        listaSemestre = new ArrayList<>();
        listaSemestre.add("Seleccionar Semestre");
        listaSemestre.add("2020 - 1");
        listaSemestre.add("2020 - 2");
        listaSemestre.add("2021 - 1");
        listaSemestre.add("2021 - 2");

        ArrayAdapter<CharSequence> adaptadorSemestre = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaSemestre);
        semestre.setAdapter(adaptadorSemestre);
//      Cargando Spinner dia
        dia = findViewById(R.id.spinner_dia);

        listaDia = new ArrayList<>();
        listaDia.add("Seleccionar Dia");
        listaDia.add("Lunes");
        listaDia.add("Martes");
        listaDia.add("Miercoles");
        listaDia.add("Jueves");
        listaDia.add("Viernes");
        listaDia.add("Sabado");
        listaDia.add("Domingo");

        ArrayAdapter<CharSequence> adaptadorDia = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaDia);
        dia.setAdapter(adaptadorDia);
//      Cargando Spinner hora
        hora = findViewById(R.id.spinner_hora);

        listaHora = new ArrayList<>();
        listaHora.add("Seleccionar Hora");
        listaHora.add("7:00 - 8:00");
        listaHora.add("8:00 - 9:00");
        listaHora.add("9:00 - 10:00");
        listaHora.add("10:00 - 11:00");
        listaHora.add("11:00 - 12:00");

        ArrayAdapter<CharSequence> adaptadorHora = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaHora);
        hora.setAdapter(adaptadorHora);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(crear_horario.this, decano_gestion.class));
        finish();

    }

    public void crear_horario(View view){

        String selectAsignatura = asignatura.getSelectedItem().toString();
        String[] codigo = selectAsignatura.split(" - ");
        String codigo1 = codigo[0];

        String selectSemestre = semestre.getSelectedItem().toString();
        String selectDia = dia.getSelectedItem().toString();
        String selectHora = hora.getSelectedItem().toString();
        int ID_docente = Integer.parseInt(getIntent().getExtras().getString("ID_usuario"));
        EditText et_salon = findViewById(R.id.et_salon);
        String salon = et_salon.getText().toString();

        if (selectAsignatura.equals("Seleccionar Asignatura") || selectSemestre.equals("Seleccionar Semestre")|| selectDia.equals("Seleccionar Dia")|| selectHora.equals("Seleccionar Hora") || salon.equals("")) {
            Toast.makeText(this, "Complete los campos para crear una asignatura", Toast.LENGTH_SHORT).show();
        } else {

            ContentValues registro = new ContentValues();

            registro.put("ID_docente", ID_docente);
            registro.put("cod_asignatura", codigo1);
            registro.put("semestre", selectSemestre);
            registro.put("dia_semana", selectDia);
            registro.put("hora", selectHora);
            registro.put("salon", salon);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.insert("Horario", null, registro);
            bd.close();

            Toast.makeText(this, "Horario creado con exito", Toast.LENGTH_SHORT).show();


            Intent i = new Intent(this, docente_gestiona.class);
                    i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
                    i.putExtra("nombre", getIntent().getExtras().getString("nombre"));
                    i.putExtra("facultad", getIntent().getExtras().getString("facultad"));
            startActivity(i);
        }
    }
}