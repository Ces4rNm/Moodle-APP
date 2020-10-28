package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class crear_horario extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    Spinner docente, asignatura, semestre, dia, hora;
    String salon;
    ArrayList<String> listaDocentes, listaAsignaturas, listaSemestre, listaDia, listaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_horario);

        // Cargando Spinner docentes
        docente = findViewById(R.id.spinner_docente);

        SQLiteDatabase bd2 = admin.getWritableDatabase();
        Cursor filas2 = bd2.rawQuery("select ID_usuario, nombre, facultad from Usuario where rol='2';", null);

        listaDocentes = new ArrayList<>();
        listaDocentes.add("Seleccionar Docente");

        if(filas2.moveToFirst()) {
            do {
                listaDocentes.add(filas2.getString(0)+" - "+filas2.getString(1));
            } while (filas2.moveToNext());
        }

        ArrayAdapter<CharSequence> adaptadorDocentes = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, listaDocentes);
        docente.setAdapter(adaptadorDocentes);
        // Cargando Spinner asignatura
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
        // Cargando Spinner semestre
        semestre = findViewById(R.id.spinner_semestre);

        listaSemestre = new ArrayList<>();
        listaSemestre.add("Seleccionar Semestre");
        listaSemestre.add("2020 - 1");
        listaSemestre.add("2020 - 2");
        listaSemestre.add("2021 - 1");
        listaSemestre.add("2021 - 2");

        ArrayAdapter<CharSequence> adaptadorSemestre = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listaSemestre);
        semestre.setAdapter(adaptadorSemestre);
        // Cargando Spinner dia
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
        // Cargando Spinner hora
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

    public void crear_horario(View view){

        String selectDocente = docente.getSelectedItem().toString();
        String[] codigo2 = selectDocente.split(" - ");
        String ID_docente = codigo2[0];

        String selectAsignatura = asignatura.getSelectedItem().toString();
        String[] codigo = selectAsignatura.split(" - ");
        String codigo1 = codigo[0];

        String selectSemestre = semestre.getSelectedItem().toString();
        String selectDia = dia.getSelectedItem().toString();
        String selectHora = hora.getSelectedItem().toString();
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

            Intent i = new Intent(this, decano_gestion.class);
            startActivity(i);
        }
    }
}