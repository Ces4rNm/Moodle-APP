package com.proyect.moodle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.proyect.moodle.AppClass.Docente.vista_dia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class vista_semana extends AppCompatActivity {

    String dia = "";

    public void ver_semestre(View view) {
        Intent i = new Intent(this, vista_semestre.class );
        i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
        startActivity(i);
    }

    public void ver_dia(View view) {
        Intent i = new Intent(this, vista_dia.class );
        dia = obtener_semanas().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre();
        i.putExtra("dia", dia);
        i.putExtra("validadorSemana", "1");
        i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
        i.putExtra("semestre", getIntent().getExtras().getString("semestre"));
        startActivity(i);
    }

    private RecyclerView rv_vista_semanas;
    private rv_semanas_adaptador adaptador_semanas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String validadorSemestre = getIntent().getExtras().getString("validadorSemestre");

        setContentView(R.layout.activity_vista_semana);

        if (validadorSemestre.equals("1")){
            TextView tv_semestre = findViewById(R.id.text_semestre);
            String semestreNombre = getIntent().getExtras().getString("semestre");
            Log.d("SEMESTRE", String.valueOf(semestreNombre));
            tv_semestre.setText(semestreNombre);
        }

        rv_vista_semanas = (RecyclerView)findViewById(R.id.rv_semestre);
        rv_vista_semanas.setLayoutManager(new LinearLayoutManager(this));

        adaptador_semanas = new rv_semanas_adaptador(obtener_semanas());
        adaptador_semanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_semanas().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                ver_dia(view);
            }
        });
        rv_vista_semanas.setAdapter(adaptador_semanas);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(vista_semana.this, vista_dia.class));
        finish();

    }


    public List<semana_modelo> obtener_semanas() {
        List<semana_modelo> semana = new ArrayList<>();
        semana.add(new semana_modelo("Lunes"));
        semana.add(new semana_modelo("Martes"));
        semana.add(new semana_modelo("Miercoles"));
        semana.add(new semana_modelo("Jueves"));
        semana.add(new semana_modelo("Viernes"));
        semana.add(new semana_modelo("Sabado"));
        semana.add(new semana_modelo("Domingo"));

        return semana;
    }
}
