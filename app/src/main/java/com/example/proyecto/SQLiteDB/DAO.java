package com.example.proyecto.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto.Modelo.Seccion;
import com.example.proyecto.Modelo.Tarea;

import java.util.ArrayList;

public class DAO {
    public DAO() {
    }

    public static void insertarTarea(Context c, Tarea tarea){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getWritableDatabase();

        int idUsuario = tarea.getIdUsuario();
        int idSeccion = tarea.getIdSeccion();
        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        String prioridad = tarea.getPrioridad();
        String fecha = tarea.getFecha();

        ContentValues registro = new ContentValues();
        registro.put("id_usuario", idUsuario);
        registro.put("id_seccion", idSeccion);
        registro.put("titulo", titulo);
        registro.put("descripcion", descripcion);
        registro.put("prioridad", prioridad);
        registro.put("fecha", fecha);

        db.insert("tareas", null, registro);
        db.close();
    }

    public static void insertarSeccion(Context c, Seccion seccion){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getWritableDatabase();

        int idUsuario = seccion.getId_usuario();
        String nombre = seccion.getNombre();

        ContentValues registro = new ContentValues();
        registro.put("id_usuario", idUsuario);
        registro.put("nombre", nombre);

        db.insert("secciones",null,registro);
        db.close();

    }
    public static ArrayList<Tarea> mostrarTareas(Context c, String idUsuario){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tareas WHERE id_usuario=" + idUsuario, null);
        ArrayList<Tarea> tareas = new ArrayList<>();

        Tarea tarea;
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                tarea = new Tarea();
                tarea.setId(cursor.getInt(0));
                tarea.setIdUsuario(cursor.getInt(1));
                tarea.setIdSeccion(cursor.getInt(2));
                tarea.setTitulo(cursor.getString(3));
                tarea.setDescripcion(cursor.getString(4));
                tarea.setPrioridad(cursor.getString(5));
                tarea.setFecha(cursor.getString(6));
                tareas.add(tarea);
            }while (cursor.moveToNext());
        }
        db.close();
        return tareas;
    }

    public static ArrayList<String> mostrarSecciones(Context c, String idUsuario){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nombre FROM secciones WHERE id_usuario=" + idUsuario, null);
        ArrayList<String> secciones = new ArrayList<>();

        String nomSeccion = " ";
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                nomSeccion = cursor.getString(0);
                secciones.add(nomSeccion);
            }while (cursor.moveToNext());
        }
        db.close();
        return secciones;
    }
    public static int idSeccion(Context c, String nombre, int idUsuario){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id FROM secciones WHERE nombre='" + nombre + "' AND id_usuario=" + idUsuario, null);
        int id = 0;
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                id =cursor.getInt(0);

            }while (cursor.moveToNext());
        }
        db.close();
        return id;
    }
}
