package com.example.proyecto.Modelo;

public class Cursos {
    String id_curso;
    String nombre;

    String id_usuario;

    public Cursos(String id_curso, String nombre, String id_usuario) {
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.id_usuario = id_usuario;
    }

    public String getId_curso() {
        return id_curso;
    }

    public void setId_curso(String id_curso) {
        this.id_curso = id_curso;
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
