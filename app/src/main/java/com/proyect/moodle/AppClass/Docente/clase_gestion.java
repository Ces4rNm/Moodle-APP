package com.proyect.moodle.AppClass.Docente;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

public class clase_gestion extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    int ID_docente,cod_asignatura;

    public void iniciar_clase(View view) {
        TextView tv_tema = findViewById(R.id.et_tema);
        String tema = tv_tema.getText().toString();

        if (!tema.equals("")) {
            //  asistencia profesor
            // 0 = inasistencia
            // 1 = asistencia

            // estado clase
            // 0 = ausenta
            // 1 = inicia
            // 2 = termina
            gestionar_clase(ID_docente,cod_asignatura,tema,1,1);

            Intent i = new Intent(this, clase_activa.class );

            SQLiteDatabase bd = admin.getWritableDatabase();

            Cursor fila = bd.rawQuery("select MAX(cod_clase) from Clase;", null);
            if (fila.moveToFirst()) {
                i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_docente"));
                i.putExtra("cod_clase", fila.getString(0));

                TextView tv_dia = findViewById(R.id.text_materia);
                String dia = tv_dia.getText().toString();
                i.putExtra("dia", dia);

                TextView tv_hora = findViewById(R.id.text_hora);
                String hora = tv_hora.getText().toString();
                i.putExtra("hora", hora);

                TextView tv_semana = findViewById(R.id.text_semana);
                String semana = tv_semana.getText().toString();
                i.putExtra("nombre", semana);

                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(),"No se encontro una clase registrada", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Ingresé el tema de la clase", Toast.LENGTH_SHORT).show();
        }
    }
    public void ausentar_clase(View view) {
        TextView tv_motivo = findViewById(R.id.et_motivo);
        String motivo = tv_motivo.getText().toString();

        if (!motivo.equals("")) {
            //  asistencia profesor
            // 0 = inasistencia
            // 1 = asistencia

            // estado clase
            // 0 = ausenta
            // 1 = inicia
            // 2 = termina
            gestionar_clase(ID_docente,cod_asignatura,motivo,0,0);

            Toast.makeText(getApplicationContext(),"Clase ausentada", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, vista_dia.class );
            i.putExtra("validadorSemana", "0");
            i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_docente"));
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),"Ingresé el motivo de la ausencia", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_gestion);

        TextView tv_nombre = findViewById(R.id.text_materia);
        TextView tv_hora = findViewById(R.id.text_hora);
        TextView tv_semana = findViewById(R.id.text_semana);

        String materiaNombre = getIntent().getExtras().getString("nombre");
        String horaValor = getIntent().getExtras().getString("hora");
        String semanaNombre = getIntent().getExtras().getString("dia");

        cod_asignatura = Integer.parseInt(getIntent().getExtras().getString("cod_asignatura"));
        ID_docente = Integer.parseInt(getIntent().getExtras().getString("ID_docente"));

        tv_nombre.setText(materiaNombre);
        tv_hora.setText(horaValor);
        tv_semana.setText(semanaNombre);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, docente_gestion.class );
        i.putExtra("ID_usuario", ID_docente);
        i.putExtra("validadorSemana", "0");
        startActivity(i);
        finish();
    }

    public void gestionar_clase(int ID_docente, int cod_asignatura, String descripcion, int asistencia_profesor, int estado) {
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("ID_docente", ID_docente);
        registro.put("cod_asignatura", cod_asignatura);
        registro.put("descripcion", descripcion);
        registro.put("asistencia_profesor", asistencia_profesor);
        registro.put("estado", estado);

        bd.insert("Clase", null, registro);
        bd.close();
    }
}
