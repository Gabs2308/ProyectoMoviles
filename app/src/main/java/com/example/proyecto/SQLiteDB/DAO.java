package com.example.proyecto.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto.Modelo.Tarea;

public class DAO {
    public DAO() {
    }

    public static void insertarTarea(Context c, Tarea tarea){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getWritableDatabase();

        int idUsuario = tarea.getIdUsuario();
        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        String prioridad = tarea.getPrioridad();
        String lista = tarea.getLista();
        String fecha = tarea.getFecha();

        ContentValues registro = new ContentValues();
        registro.put("id_usuario", idUsuario);
        registro.put("titulo", titulo);
        registro.put("descripcion", descripcion);
        registro.put("prioridad", prioridad);
        registro.put("lista", lista);
        registro.put("fecha", fecha);

        db.insert("tareas", null, registro);
        db.close();
    }
}
