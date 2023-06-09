package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.proyecto.SQLiteDB.SQLiteOpenHelperManager

class MainActivity : AppCompatActivity() {
    var SQLiteOpenHelperManager = SQLiteOpenHelperManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SQLiteOpenHelperManager = SQLiteOpenHelperManager(this)
        SQLiteOpenHelperManager.crearTablaUsuarios()

//        SQLiteOpenHelperManager.crearTablaSecciones()
//        SQLiteOpenHelperManager.crearTablaTareas()
//
//        SQLiteOpenHelperManager.burnedData()

        val loginButton = findViewById<Button>(R.id.loginbtn)

        loginButton.setOnClickListener {
            val usuarioText = findViewById<EditText>(R.id.usuariotext)
            val passwordText = findViewById<EditText>(R.id.passwordtext)

            val usuario = usuarioText.text.toString()
            val password = passwordText.text.toString()

            validarUsuario(usuario, password)

            // Crear la tabla de usuarios
        }
    }

    @SuppressLint("Range")
    fun validarUsuario(usuario: String, password: String) {
        val db = SQLiteOpenHelperManager.readableDatabase

        val insertQuery1 = "INSERT INTO usuarios (nombre, password) VALUES ('Gabriel', 1111);"
        val insertQuery2 = "INSERT INTO usuarios (nombre, password) VALUES ('Keisy', 2222);"
        val insertQuery3 = "INSERT INTO usuarios (nombre, password) VALUES ('Raquel', 3333);"
        val insertQuery4 = "INSERT INTO usuarios (nombre, password) VALUES ('Karo', 4444);"

        db.execSQL(insertQuery1)
        db.execSQL(insertQuery2)
        db.execSQL(insertQuery3)
        db.execSQL(insertQuery4)

        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE nombre=? AND password=?",
            arrayOf(usuario, password)
        )
        if (cursor.moveToFirst()) {
            val intent = Intent(this, ToDoList::class.java)
            val idUsuario = cursor.getString(cursor.getColumnIndex("id"))
            intent.putExtra("IdUsuario", idUsuario)
            startActivity(intent)
            cursor.close()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Error de autenticación")
                .setMessage("El Usuario o la contraseña son incorrectos.")
                .setPositiveButton("OK", null)
                .show()
        }
        cursor.close() // Cierra el cursor cuando ya no lo necesites
    }

}
