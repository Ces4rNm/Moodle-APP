package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;

public class crear_asignatura extends AppCompatActivity {
    EditText et_nombre_asignatura, et_credito_asignatura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_asignatura);

        et_nombre_asignatura = (EditText)findViewById(R.id.et_nombre_asignatura);
        et_credito_asignatura = (EditText)findViewById(R.id.et_credito_asignatura);
    }

    public void crearAsignatura(View view) {

        String nombre = et_nombre_asignatura.getText().toString();
        String n_creditos = et_credito_asignatura.getText().toString();

        if (nombre.equals("") || n_creditos.equals("")) {
            Toast.makeText(this, "Complete el formulario de la asignatura", Toast.LENGTH_SHORT).show();
        } else {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();

            registro.put("nombre", nombre);
            registro.put("n_creditos", n_creditos);


            bd.insert("Asignatura", null, registro);
            bd.close();

            et_nombre_asignatura.setText("");
            et_credito_asignatura.setText("");

            Toast.makeText(this, "Asignatura creada con exito", Toast.LENGTH_SHORT).show();
        }
    }
}