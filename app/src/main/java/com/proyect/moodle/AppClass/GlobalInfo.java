package com.proyect.moodle.AppClass;

public class GlobalInfo {
    public static String ID_usuario = "", Rol = "", Nombre = "";

    public GlobalInfo(String ID_usuario, String Rol, String Nombre) {
        this.ID_usuario = ID_usuario;
        this.Rol = Rol;
        this.Nombre = Nombre;
    }

    public static String getID_usuario() {
        return ID_usuario;
    }

    public static void setID_usuario(String ID_usuario) {
        GlobalInfo.ID_usuario = ID_usuario;
    }

    public static String getRol() {
        return Rol;
    }

    public static void setRol(String rol) {
        Rol = rol;
    }

    public static String getNombre() {
        return Nombre;
    }

    public static void setNombre(String nombre) {
        Nombre = nombre;
    }
}
