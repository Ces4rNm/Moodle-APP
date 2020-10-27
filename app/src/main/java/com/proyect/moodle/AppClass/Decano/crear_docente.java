package com.proyect.moodle.AppClass.Decano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.AdminSQLiteOpenHelper;

public class crear_docente extends AppCompatActivity {
    EditText et_docente_ID_usuario,et_docente_nombre, et_docente_edad, et_docente_estudio, et_docente_usuario, et_docente_password, et_docente_falcultad;
    RadioButton rbnM,rbnF;
    RadioGroup radioGroupSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_docente);

        et_docente_ID_usuario=(EditText)findViewById(R.id.et_docente_documento);
        et_docente_nombre=(EditText)findViewById(R.id.et_docente_nombre);
        et_docente_edad=(EditText)findViewById(R.id.et_docente_edad);
        et_docente_estudio=(EditText)findViewById(R.id.et_docente_estudio);
        et_docente_falcultad=(EditText)findViewById(R.id.et_docente_falcultad);
        et_docente_usuario=(EditText)findViewById(R.id.et_docente_usuario);
        et_docente_password=(EditText)findViewById(R.id.et_docente_password);

        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        rbnM = findViewById(R.id.rbnM);
        rbnF = findViewById(R.id.rbnF);
    }

    public void registrarDocente(View view) {

        String documento = et_docente_ID_usuario.getText().toString();
        String nombre = et_docente_nombre.getText().toString();
        String edad = et_docente_edad.getText().toString();
        String estudio = et_docente_estudio.getText().toString();
        String usuario = et_docente_usuario.getText().toString();
        String password = et_docente_password.getText().toString();
        String facultad = et_docente_falcultad.getText().toString();

        if ((!documento.equals("") && !nombre.equals("") && !edad.equals("") && !estudio.equals("") && !usuario.equals("") && !password.equals("") && !facultad.equals(""))==false || (radioGroupSexo.getCheckedRadioButtonId()!=-1)==false) {
            Toast.makeText(this, "Complete el formulario del docente", Toast.LENGTH_SHORT).show();
        } else {
            String sexo = "";

            if (rbnM.isChecked()==true) {
                sexo = "M";
            } else if (rbnF.isChecked()==true) {
                sexo = "F";
            } else {
                Toast.makeText(this, "Selecciona el sexo del docuente", Toast.LENGTH_SHORT).show();
            }

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String rol = "2";

            ContentValues registro = new ContentValues();

            registro.put("ID_usuario", documento);
            registro.put("nombre", nombre);
            registro.put("edad", edad);
            registro.put("sexo", sexo);
            registro.put("estudios", estudio);
            registro.put("usuario", usuario);
            registro.put("password", password);
            registro.put("facultad", facultad);
            registro.put("rol", rol);

            bd.insert("Usuario", null, registro);
            bd.close();

            et_docente_ID_usuario.setText("");
            et_docente_nombre.setText("");
            et_docente_edad.setText("");
            et_docente_estudio.setText("");
            et_docente_usuario.setText("");
            et_docente_password.setText("");
            et_docente_falcultad.setText("");
            rbnM.setChecked(false);
            rbnF.setChecked(false);

            Toast.makeText(this, "Docente creado con exito",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
