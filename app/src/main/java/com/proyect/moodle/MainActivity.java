package com.proyect.moodle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.proyect.moodle.AppClass.Decano.decano_gestion;
import com.proyect.moodle.AppClass.Docente.docente_gestion;
import com.proyect.moodle.AppClass.GlobalInfo;
import com.proyect.moodle.Retrofit.Interface.ApiInterface;
import com.proyect.moodle.Retrofit.Model.ResData;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalInfo.Rol = "";
        GlobalInfo.ID_usuario = "";
        GlobalInfo.Nombre = "";
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void ingresar(View view) {

        EditText tv_correo = findViewById(R.id.et_correo);
        String correo = tv_correo.getText().toString();

        EditText tv_pass = findViewById(R.id.et_pass);
        String pass = tv_pass.getText().toString();

        if (!correo.equals("") || !pass.equals("")) {
            ApiInterface login = ApiInterface.retrofit.create(ApiInterface.class);
            Call<ResData> response = login.serLogin(correo, pass);
            response.enqueue(new Callback<ResData>() {
                @Override
                public void onResponse(Call<ResData> call, Response<ResData> response) {
                    if(response.isSuccessful()) {
                        //Log.e("TAG", "response: "+new Gson().toJson(response.body()));
                        Log.d("Log",response.raw().toString());
                        Log.d("Log JSON",response.body().toString());
                        if (response.body().getCode().equals("0")) {
                            String data = response.body().getData();
                            try {
                                JSONObject json = new JSONObject(data);
                                String rol = json.getString("rol");
                                String ID_usuario = json.getString("ID_usuario");
                                String nombre = json.getString("nombre");

                                if (rol.equals("1")) {
                                    //  Decano
                                    Intent i = new Intent(MainActivity.this, decano_gestion.class );
                                    GlobalInfo.Rol = rol;
                                    GlobalInfo.ID_usuario = ID_usuario;
                                    GlobalInfo.Nombre = nombre;
                                    startActivity(i);
                                } else if (rol.equals("2")) {
                                    //  Docente
                                    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                                    boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

                                    if (enabled) {
                                        Intent i = new Intent(MainActivity.this, docente_gestion.class );
                                        GlobalInfo.Rol = rol;
                                        GlobalInfo.ID_usuario = ID_usuario;
                                        GlobalInfo.Nombre = nombre;
                                        startActivity(i);
                                    } else{
                                        Toast.makeText(getApplicationContext(),"Por favor, active el GPS para iniciar sesión", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Error, ROL no definido:"+rol,Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "("+response.body().getCode()+") "+response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("notSuccessful", String.valueOf(response));
                    }
                }
                @Override
                public void onFailure(Call<ResData> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),"Ingrese correo y contraseña", Toast.LENGTH_SHORT).show();
        }

    }

}
