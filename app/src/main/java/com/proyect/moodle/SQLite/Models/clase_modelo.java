package com.proyect.moodle.SQLite.Models;

public class clase_modelo {
   String id_clase, nombre_clase, creditos, nombre_docente, asistencia, descripcion;

    public clase_modelo(String id_clase, String nombre_clase, String creditos, String nombre_docente, String asistencia, String descripcion) {
        this.id_clase = id_clase;
        this.nombre_clase = nombre_clase;
        this.creditos = creditos;
        this.nombre_docente = nombre_docente;
        this.asistencia = asistencia;
        this.descripcion = descripcion;
    }

    public String getId_clase() {
        return id_clase;
    }

    public void setId_clase(String id_clase) {
        this.id_clase = id_clase;
    }

    public String getNombre_clase() {
        return nombre_clase;
    }

    public void setNombre_clase(String nombre_clase) {
        this.nombre_clase = nombre_clase;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public String getNombre_docente() {
        return nombre_docente;
    }

    public void setNombre_docente(String nombre_docente) {
        this.nombre_docente = nombre_docente;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
