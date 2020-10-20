package com.proyect.moodle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class confirmar_gps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_gps);
    }

    public void ver_horario(View view) {
        Intent i = new Intent(this, vista_dia.class );
        i.putExtra("validadorSemana", "0");
        i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
        startActivity(i);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(confirmar_gps.this, MainActivity.class));
        finish();

    }
}
