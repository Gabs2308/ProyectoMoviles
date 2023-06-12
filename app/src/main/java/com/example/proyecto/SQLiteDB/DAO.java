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
    public static ArrayList<String> mostrarTareas(Context c, String idUsuario){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT titulo FROM tareas WHERE id_usuario=" + idUsuario, null);
        ArrayList<String> tareas = new ArrayList<>();

        String tarea;
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                tarea = cursor.getString(0);
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

    // Metodos para modificar tarea
    public static int getIDTarea(Context c, String nombreTarea, String idUsuario){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id FROM tareas WHERE titulo='" + nombreTarea + "' AND id_usuario=" + idUsuario, null);
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

    public static Tarea retornarTarea(Context c, String idTarea){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase baseDatos = manager.getWritableDatabase();
        Cursor cur = baseDatos.rawQuery("SELECT * FROM tareas WHERE id =" + idTarea,null);
        Tarea aux;
        if (cur.moveToNext()) {
            aux = new Tarea();
            aux.setId(cur.getInt(0));
            aux.setIdUsuario(cur.getInt(1));
            aux.setIdSeccion(cur.getInt(2));
            aux.setTitulo(cur.getString(3));
            aux.setDescripcion(cur.getString(4));
            aux.setPrioridad(cur.getString(5));
            aux.setFecha(cur.getString(6));
            baseDatos.close();
            return aux;
        }
        return null;
    }

    public static int modificarTarea(Context c, Tarea tarea){
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase baseDatos = manager.getWritableDatabase();
        int cantidad = 0;

        Integer idTarea = tarea.getId();
        int idSeccion = tarea.getIdSeccion();
        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        String prioridad = tarea.getPrioridad();
        String fecha = tarea.getFecha();

        ContentValues registro = new ContentValues();
        registro.put("id_seccion", idSeccion);
        registro.put("titulo", titulo);
        registro.put("descripcion", descripcion);
        registro.put("prioridad", prioridad);
        registro.put("fecha", fecha);

        cantidad = baseDatos.update("tareas",registro,"id=" + idTarea,null);
        baseDatos.close();
        return cantidad;
    }

    public static String nombreSeccion(Context c,int idSeccion) {
        SQLiteOpenHelperManager manager = new SQLiteOpenHelperManager(c);
        SQLiteDatabase db = manager.getReadableDatabase();
        String sqlQuery = "SELECT nombre FROM secciones WHERE id = " + idSeccion;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        String nombre = " ";
        if (cursor != null && cursor.moveToFirst()) {
            int nombreColumnIndex = cursor.getColumnIndex("nombre");
            nombre = cursor.getString(nombreColumnIndex);
            cursor.close();
            db.close();
            return nombre;
        }
        return "";
    }
    // Fin de metodos de modificar

}
