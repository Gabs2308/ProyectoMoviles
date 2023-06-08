package com.example.proyecto.Modelo;

public class Seccion {
    String id;
    String nombre;
    String id_usuario;

    public Seccion(String nombre, String id_usuario) {
        this.nombre = nombre;
        this.id_usuario = id_usuario;
    }

    public Seccion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
