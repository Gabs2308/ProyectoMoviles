package com.example.proyecto

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.Modelo.Seccion
import com.example.proyecto.SQLiteDB.DAO
import com.example.proyecto.SQLiteDB.SQLiteOpenHelperManager

class ToDoList : AppCompatActivity() {
    var SQLiteOpenHelperManager = SQLiteOpenHelperManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        //Id usuario enviado de la instancia pasada
        val bundle = intent.extras
        val idUsuario = bundle!!.getString("IdUsuario")
        val nuevaSeccion: EditText = findViewById(R.id.nuevaSeccion)
        val nuevaSeccionBtn: Button = findViewById(R.id.agregarSeccion)
        val nuevatareabtn = findViewById<ImageView>(R.id.nuevatareabtn)


        nuevatareabtn.setOnClickListener {

            val intent = Intent(this, New::class.java)
            intent.putExtra("IdUsuario", idUsuario)
            startActivity(intent)
        }

        nuevaSeccionBtn.setOnClickListener{
            val newSeccion: Seccion = Seccion(
                nuevaSeccion.text.toString(),
                idUsuario!!.toInt()
            )
            validarSeccion(newSeccion.nombre,newSeccion)
            nuevaSeccion.text.clear()

        }
    }

    @SuppressLint("Range")
    fun validarSeccion(nombre: String, seccion: Seccion) {
        val db = SQLiteOpenHelperManager.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM secciones WHERE nombre=?",
            arrayOf(nombre)
        )
        if (cursor.moveToFirst()) {
            val intent = Intent(this, ToDoList::class.java)
            val nombreSeccion = cursor.getString(cursor.getColumnIndex("nombre"))
            intent.putExtra("nombreSeccion", nombreSeccion)
            AlertDialog.Builder(this)
                .setTitle("Sección existente")
                .setMessage("Una sección con este nombre ya existe.")
                .setPositiveButton("OK", null)
                .show()
            cursor.close()
        } else {
            DAO.insertarSeccion(this, seccion)
            AlertDialog.Builder(this)
                .setTitle("Éxito")
                .setMessage("La sección se ha agregado correctamente.")
                .setPositiveButton("OK", null)
                .show()
        }
        cursor.close()
    }
}
