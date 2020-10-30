package com.proyect.moodle.AppClass.Docente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.moodle.AppClass.Decano.decano_gestion;
import com.proyect.moodle.AppClass.Decano.listado_clases;
import com.proyect.moodle.AppClass.GlobalInfo;
import com.proyect.moodle.MainActivity;
import com.proyect.moodle.R;

public class docente_gestion extends AppCompatActivity {

    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_gestion);

        TextView tvRol = findViewById(R.id.tvRol);
        if (GlobalInfo.Rol.equals("1")) {
            //  Decano
            tvRol.setText("Rol: Decano");
        } else if (GlobalInfo.Rol.equals("2")) {
            //  Docente
            tvRol.setText("Rol: Docente");
        } else {
            tvRol.setText("Rol: Undefined");
            Toast.makeText(this, "Error, ROL no definido:"+tvRol,Toast.LENGTH_SHORT).show();
        }

        TextView tvNombre = findViewById(R.id.tvNombreUsuario);
        tvNombre.setText(GlobalInfo.Nombre);

        vibe = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        final ImageView img = (ImageView) findViewById(R.id.ivExit);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibe.vibrate(100);
                startActivity(new Intent(docente_gestion.this, MainActivity.class));
                finish();
            }
        });
    }

    public void listar_horario(View view) {
        Intent i = new Intent(this, vista_dia.class );
        i.putExtra("tipo_vista", true);
        startActivity(i);
    }

    public void filtrar_todos(View view) {
        Intent i = new Intent(this, filtrar_horario.class );
        startActivity(i);
    }

    public void clases_gestionadas(View view) {
        Intent i = new Intent(this, listado_clases.class );
        startActivity(i);
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(docente_gestion.this, MainActivity.class));
        finish();
    }
}