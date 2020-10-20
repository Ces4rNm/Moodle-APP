package com.proyect.moodle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class vista_semestre extends AppCompatActivity {

    public void ver_semana(View view) {
        Intent i = new Intent(this, vista_semana.class );
        String semestre = obtener_semestre().get(rv_vista_semestres.getChildAdapterPosition(view)).getNombre();
        i.putExtra("semestre", semestre);
        i.putExtra("validadorSemestre", "1");
        i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
        startActivity(i);
    }

    private RecyclerView rv_vista_semestres;
    private rv_semestres_adaptador adaptador_semestre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_semestre);

        rv_vista_semestres = (RecyclerView)findViewById(R.id.rv_semestre);
        rv_vista_semestres.setLayoutManager(new LinearLayoutManager(this));

        adaptador_semestre = new rv_semestres_adaptador(obtener_semestre());
        adaptador_semestre.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Texto:"+obtener_semanas().get(rv_vista_semanas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                ver_semana(view);
            }
        });
        rv_vista_semestres.setAdapter(adaptador_semestre);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(vista_semestre.this, vista_semana.class));
        finish();

    }


    public List<semana_modelo> obtener_semestre() {
        List<semana_modelo> semestre = new ArrayList<>();
        semestre.add(new semana_modelo("2020 - 1"));
        semestre.add(new semana_modelo("2020 - 2"));
        semestre.add(new semana_modelo("2021 - 1"));
        semestre.add(new semana_modelo("2021 - 2"));

        return semestre;
    }
}
