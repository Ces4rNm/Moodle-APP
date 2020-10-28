package com.proyect.moodle.SQLite.Models;

public class semana_modelo {
    private String nombre;

    public semana_modelo() {
    }

    public semana_modelo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
