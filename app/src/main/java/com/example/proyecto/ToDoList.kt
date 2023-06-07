package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ToDoList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        //Id usuario enviado de la instancia pasada
        val bundle = intent.extras
        val idUsuario = bundle!!.getString("IdUsuario")

        val nuevatareabtn = findViewById<ImageView>(R.id.nuevatareabtn)


        nuevatareabtn.setOnClickListener {

            val intent = Intent(this, New::class.java)
            intent.putExtra("IdUsuario", idUsuario)
            startActivity(intent)
        }
    }
}
