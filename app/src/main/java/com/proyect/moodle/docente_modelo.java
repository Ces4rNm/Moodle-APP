package com.proyect.moodle;

public class docente_modelo {
    private int ID_usuario;
    private String nombre, facultad;

    public docente_modelo(int ID_usuario, String nombre, String facultad) {
        this.ID_usuario = ID_usuario;
        this.nombre = nombre;
        this.facultad = facultad;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
}
