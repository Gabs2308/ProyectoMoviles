package com.example.proyecto

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.SQLiteDB.DAO

class Modify : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        //Id usuario enviado de la instancia pasada
        val bundle = intent.extras
        val idUsuario = bundle!!.getString("IdUsuario")
        lateinit var fecha: String

        val cancelbtn: Button =  findViewById(R.id.btnCancel)

        val enviarbtn: Button = findViewById(R.id.btnAceptar)
        val titulo: EditText = findViewById(R.id.editTextTextTittle)
        val descripcion: EditText = findViewById(R.id.editTextTextDescript)
        val spinnerPrioridad: Spinner = findViewById(R.id.spPriority)
        val spinnerSecciones: Spinner = findViewById(R.id.spSecciones)
        val btnFecha: Button = findViewById(R.id.btnFecha)


        val prioridades = listOf("Seleccionar una prioridad","1", "2", "3")
        val secciones = DAO.mostrarSecciones(this, idUsuario)
        secciones.add(0, "Seleccione una sección")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioridades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrioridad.adapter = adapter

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, secciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSecciones.adapter = adapter2

        // Seteo de Valores
        val idTarea = bundle!!.getInt("Tarea")
        val tarea = DAO.retornarTarea(this,idTarea.toString())
        titulo.setText(tarea.titulo)
        descripcion.setText(tarea.descripcion)
        val prioridadPosicion: Int = adapter.getPosition(tarea.prioridad)
        spinnerPrioridad.setSelection(prioridadPosicion)
        val nombreSeccion = DAO.nombreSeccion(this, tarea.idSeccion)
        val seccionPos: Int = adapter2.getPosition(nombreSeccion)
        spinnerSecciones.setSelection(seccionPos)
        btnFecha.text = tarea.fecha
        fecha = tarea.fecha

        btnFecha.setOnClickListener {
            val partes = btnFecha.text.split("/")
            val dia = partes[0].toInt()
            val mes = partes[1].toInt()
            val annio = partes[2].toInt()

            val calendar = Calendar.getInstance()
            //Cargando la fecha
            calendar.set(Calendar.YEAR, annio)
            calendar.set(Calendar.MONTH, mes - 1)
            calendar.set(Calendar.DAY_OF_MONTH, dia)
            //
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                   btnFecha.text = selectedDate.toString()
                    fecha = selectedDate
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        // Listeners
        cancelbtn.setOnClickListener {
            val intent = Intent(this, ToDoList::class.java)
            intent.putExtra("IdUsuario", idUsuario)
            startActivity(intent)
        }

        // Aqui se modifica el btn de enviar para nueva tarea o modificar
        enviarbtn.setOnClickListener {
            tarea.setTitulo(titulo.text.toString())
            tarea.setDescripcion(descripcion.text.toString())
            tarea.setPrioridad(spinnerPrioridad.getItemAtPosition(spinnerPrioridad.selectedItemPosition).toString())
            tarea.setIdSeccion(
                DAO.idSeccion(
                    this,
                        spinnerSecciones.getItemAtPosition(spinnerSecciones.selectedItemPosition).toString(),
                        idUsuario!!.toInt()
                    ))
            tarea.setFecha(fecha)
            DAO.modificarTarea(this, tarea)
            val intent = Intent(this, ToDoList::class.java)
            intent.putExtra("IdUsuario", idUsuario)
            startActivity(intent)
        }


    }
}