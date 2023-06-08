package com.example.proyecto.SQLiteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class SQLiteOpenHelperManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "proyecto";
    private static final int DATABASE_VERSION = 1;

    public SQLiteOpenHelperManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //sqLiteDatabase.execSQL(
               // "CREATE TABLE usuarios(" +
               //         "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
               //         "nombre TEXT NOT NULL," +
               //         "password TEXT NOT NULL)"
        //);




        burnedData(sqLiteDatabase);

    }

    public void crearTablaUsuarios() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS usuarios"); // eliminamos la tabla existente si existe
        db.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY, nombre TEXT, password TEXT);");
    }

    public void crearTablaTareas(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS tareas"); // eliminamos la tabla existente si existe
        db.execSQL(
               "CREATE TABLE tareas(" +
                       "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                       "id_usuario INTEGER NOT NULL,"+
                       "id_seccion INTEGER NOT NULL,"+
                       "titulo TEXT NOT NULL," +
                       "descripcion TEXT," +
                       "prioridad TEXT NOT NULL,"+
                       "lista TEXT,"+
                       "fecha TEXT NOT NULL,"+
                       "FOREIGN KEY (id_seccion) REFERENCES secciones(id),"+
                       "FOREIGN KEY (id_usuario) REFERENCES usuarios(id))"
        );
    }

    public void crearTablaSecciones(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS secciones"); // eliminamos la tabla existente si existe
        db.execSQL(
                "CREATE TABLE secciones(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "id_usuario INTEGER NOT NULL,"+
                        "nombre TEXT NOT NULL," +
                        "FOREIGN KEY (id_usuario) REFERENCES usuarios(id))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }


    private void burnedData(SQLiteDatabase sqLiteDatabase) {


        //USUARIOS----------------------------------------------------------------------------------
        //sqLiteDatabase.execSQL("insert into usuarios (nombre, password) values ('Gabriel',1111)");
       // sqLiteDatabase.execSQL("insert into usuarios (nombre, password) values ('Keisy',2222)");
       // sqLiteDatabase.execSQL("insert into usuarios (nombre, password) values ('Raquel',3333)");
       // sqLiteDatabase.execSQL("insert into usuarios (nombre, password) values ('Karo',4444)");

        //Seccion----------------------------------------------------------------------------------
       // sqLiteDatabase.execSQL("insert into Seccion (nombre) values ('Paradigmas')");
       // sqLiteDatabase.execSQL("insert into Seccion (nombre) values ('Moviles')");
       // sqLiteDatabase.execSQL("insert into Seccion (nombre) values ('Videojuegos')");
       // sqLiteDatabase.execSQL("insert into Seccion (nombre) values ('Sistemas')");



    }
}
