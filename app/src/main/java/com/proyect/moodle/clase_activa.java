package com.proyect.moodle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class clase_activa extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_activa);

        TextView tv_nombre = findViewById(R.id.text_materia);
        TextView tv_hora = findViewById(R.id.text_hora);
        TextView tv_semana = findViewById(R.id.text_semana);

        String materiaNombre = getIntent().getExtras().getString("nombre");
        String horaValor = getIntent().getExtras().getString("hora");
        String semanaNombre = getIntent().getExtras().getString("dia");

        tv_nombre.setText(materiaNombre);
        tv_hora.setText(horaValor);
        tv_semana.setText(semanaNombre);
    }

    public void terminar_clase(View view) {
        SQLiteDatabase bd = admin.getWritableDatabase();

        String cod_clase = getIntent().getExtras().getString("cod_clase");

        ContentValues registro = new ContentValues();

        registro.put("estado", 2);

        bd.update("Clase", registro, "cod_clase=" + cod_clase, null);
//        Cursor fila = bd.rawQuery("select cod_clase, ID_docente, cod_asignatura, descripcion, asistencia_profesor, estado  from Clase where cod_clase="+cod_clase+";", null);
//        if (fila.moveToFirst()) {
//            Log.d("cod_clase: ", String.valueOf(fila.getString(0)));
//            Log.d("ID_docente: ", String.valueOf(fila.getString(1)));
//            Log.d("cod_asignatura: ", String.valueOf(fila.getString(2)));
//            Log.d("descripcion: ", String.valueOf(fila.getString(3)));
//            Log.d("asistencia_profesor: ", String.valueOf(fila.getString(4)));
//            Log.d("estado: ", String.valueOf(fila.getString(5)));
//        }

        bd.close();

        Toast.makeText(getApplicationContext(), "Clase finalizada", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, vista_dia.class);
        i.putExtra("validadorSemana", "0");
        i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
        startActivity(i);
    }

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "La clase debe ser finalizada", Toast.LENGTH_SHORT).show();

    }
}

