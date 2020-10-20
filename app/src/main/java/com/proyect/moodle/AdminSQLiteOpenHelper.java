package com.proyect.moodle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Usuario(ID_usuario int primary key,nombre text, edad int,sexo text, estudios text, usuario text, password text, rol int, facultad text, fecha_registro text)");
        db.execSQL("insert into Usuario (ID_usuario, nombre, edad, sexo, estudios, usuario, password, rol, facultad, fecha_registro) " +
                "values (0, 'Diego',40,'M','Maestria','diego','123',1,'INGENIERIA','18/06/2020')");
        db.execSQL("insert into Usuario (ID_usuario, nombre, edad, sexo, estudios, usuario, password, rol, facultad, fecha_registro) " +
                "values (1, 'Iriarte',50,'M','Maestria','iriarte','123',2,'INGENIERIA','18/06/2020')");
        db.execSQL("create table Asignatura(cod_asignatura integer primary key autoincrement, nombre text, n_creditos int)");
        db.execSQL("create table Horario(ID_docente int not null, cod_asignatura integer not null, semestre text not null, dia_semana text not null, hora text not null, salon text not null, " +
                "foreign key (cod_asignatura) references Asignatura(cod_asignatura), " +
                "foreign key (ID_docente) references Usuario(ID_usuario)," +
                "primary key (ID_docente, cod_asignatura,semestre,dia_semana,hora))");
        db.execSQL("create table Clase(cod_clase integer primary key autoincrement, ID_docente int, cod_asignatura int, descripcion text, asistencia_profesor int, estado int," +
                "foreign key (ID_docente) references Usuario(ID_usuario)," +
                "foreign key (cod_asignatura) references Asignatura(cod_asignatura))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}



