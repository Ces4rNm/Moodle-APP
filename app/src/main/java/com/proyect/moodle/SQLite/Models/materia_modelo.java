package com.proyect.moodle.SQLite.Models;

public class materia_modelo  {
    private String nombre, salon, hora;

    public materia_modelo(String nombre, String salon, String hora) {
        this.nombre = nombre;
        this.salon = salon;
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
