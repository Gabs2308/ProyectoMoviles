package com.example.proyecto.Modelo;

public class Seccion {
    int id;
    String nombre;
    int id_usuario;

    public Seccion(String nombre, int id_usuario) {
        this.nombre = nombre;
        this.id_usuario = id_usuario;
    }

    public Seccion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
