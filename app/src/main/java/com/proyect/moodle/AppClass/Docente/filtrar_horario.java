package com.proyect.moodle.AppClass.Docente;

import androidx.appcompat.app.AppCompatActivity;
import com.proyect.moodle.R;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class filtrar_horario extends AppCompatActivity {

    Calendar mClndr;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_horario);

        final EditText etFecha = findViewById(R.id.etFecha);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                mClndr = Calendar.getInstance();
                int day = mClndr.get(Calendar.DAY_OF_MONTH);
                int month = mClndr.get(Calendar.MONTH);
                int year = mClndr.get(Calendar.YEAR);

                dpd = new DatePickerDialog(filtrar_horario.this, R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        etFecha.setText(mYear + "-" + (mMonth+1) + "-" + mDay);
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }
}