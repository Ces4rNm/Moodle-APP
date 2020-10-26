package com.proyect.moodle.retrofit.Model;


public class validacionUsuario {

    private String rol;
    private String iDUsuario;

    public validacionUsuario(String rol, String iDUsuario) {
        super();
        this.rol = rol;
        this.iDUsuario = iDUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(String iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

}
