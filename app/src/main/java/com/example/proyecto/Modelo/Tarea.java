package com.example.proyecto.Modelo;

public class Tarea {
    private int id;
    private int idUsuario;
    private int idSeccion;
    private String titulo;
    private String descripcion;
    private String prioridad;
    private String fecha;


    public Tarea(int idUsuario, int idSeccion, String titulo, String descripcion, String prioridad, String fecha) {
        this.idUsuario = idUsuario;
        this.idSeccion = idSeccion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fecha = fecha;
    }


    public Tarea() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getIdSeccion() {
        return idSeccion;
    }
    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
