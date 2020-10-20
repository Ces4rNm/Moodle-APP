package com.proyect.moodle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void ingresar(View view) {

        SQLiteDatabase bd = admin.getWritableDatabase();

        EditText tv_correo = findViewById(R.id.et_correo);
        String correo = tv_correo.getText().toString();

        EditText tv_pass = findViewById(R.id.et_pass);
        String pass = tv_pass.getText().toString();

        if (!correo.equals("") || !pass.equals("")) {
            Cursor fila = bd.rawQuery("select rol,ID_usuario from Usuario where usuario='"+correo+"' and password='"+pass+"';", null);

            if (fila.moveToFirst()) {
                if (fila.getString(0).equals("1")) {
                    //  Decano
                    Intent i = new Intent(this, decano_gestion.class );
                    startActivity(i);
                } else if (fila.getString(0).equals("2")) {
                    //  Docente
                    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                    boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    if (enabled) {
                        Intent i = new Intent(this, confirmar_gps.class );
                        i.putExtra("ID_usuario", fila.getString(1));
                        startActivity(i);
                    } else{
                        Intent i = new Intent(this, verificar_gps.class );
                        i.putExtra("ID_usuario", fila.getString(1));
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(this, "Error, ROL no definido:"+fila.getString(0),Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Correo y contraseña incorrecta",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Ingrese correo y contraseña", Toast.LENGTH_SHORT).show();
        }

    }

}
