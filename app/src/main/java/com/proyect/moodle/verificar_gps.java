package com.proyect.moodle;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class verificar_gps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_gps);
    }

    public void confirmar(View view) {

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (enabled) {
            Intent i = new Intent(this, confirmar_gps.class );
            i.putExtra("ID_usuario", getIntent().getExtras().getString("ID_usuario"));
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),"Por favor, active el GPS para iniciar sesión", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(verificar_gps.this, MainActivity.class));
        finish();

    }
}
