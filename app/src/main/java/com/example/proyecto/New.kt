package com.example.proyecto

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import com.example.proyecto.Modelo.Tarea
import com.example.proyecto.SQLiteDB.DAO

class New : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        //Id usuario enviado de la instancia pasada
        val bundle = intent.extras
        val idUsuario = bundle!!.getString("IdUsuario")
        lateinit var fecha: String

        val cancelbtn:Button =  findViewById(R.id.btnCancel)

        val enviarbtn: Button = findViewById(R.id.btnAceptar)
        val titulo: EditText = findViewById(R.id.editTextTextTittle)
        val descripcion: EditText = findViewById(R.id.editTextTextDescript)
        val spinnerPrioridad: Spinner = findViewById(R.id.spPriority)
        val lista: EditText = findViewById(R.id.editTextTextList)
        val btnFecha: Button = findViewById(R.id.btnFecha)

        val opciones = listOf("Select a priority","1", "2", "3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrioridad.adapter = adapter

        cancelbtn.setOnClickListener {
            val intent = Intent(this, ToDoList::class.java)
            intent.putExtra("IdUsuario", idUsuario)
            startActivity(intent)
        }

        enviarbtn.setOnClickListener{
            val tareaNueva: Tarea = Tarea(
                idUsuario!!.toInt(), titulo.text.toString(), descripcion.text.toString(),
                spinnerPrioridad.getItemAtPosition(spinnerPrioridad.selectedItemPosition).toString(), lista.text.toString(), fecha)

            DAO.insertarTarea(this, tareaNueva)
            val intent = Intent(this, ToDoList::class.java)
            intent.putExtra("IdUsuario", idUsuario)
            startActivity(intent)
        }

        btnFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Aquí puedes realizar alguna acción con la fecha seleccionada
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    // Por ejemplo, puedes mostrar la fecha en un TextView
                    btnFecha.text = selectedDate.toString()
                    fecha = selectedDate
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }
}