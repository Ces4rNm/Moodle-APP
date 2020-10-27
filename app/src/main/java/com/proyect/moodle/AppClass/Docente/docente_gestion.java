package com.proyect.moodle.AppClass.Docente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;

import com.proyect.moodle.MainActivity;
import com.proyect.moodle.R;

public class docente_gestion extends AppCompatActivity {

    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_gestion);

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
        i.putExtra("validadorSemana", "0");
        i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
        startActivity(i);
    }

    public void filtrar_todos(View view) {
        Intent i = new Intent(this, filtrar_horario.class );
        startActivity(i);
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(docente_gestion.this, MainActivity.class));
        finish();
    }
}