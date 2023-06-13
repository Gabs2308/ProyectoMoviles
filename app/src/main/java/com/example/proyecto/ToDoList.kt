package com.example.proyecto

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import com.example.proyecto.Modelo.Seccion
import com.example.proyecto.SQLiteDB.DAO
import com.example.proyecto.SQLiteDB.SQLiteOpenHelperManager

class ToDoList : AppCompatActivity() {
    var SQLiteOpenHelperManager = SQLiteOpenHelperManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_to_do_list)
        //Id usuario enviado de la instancia pasada
        val bundle = intent.extras
        val idUsuario = bundle!!.getString("IdUsuario")

        //Variables
        val nuevaSeccion: EditText = findViewById(R.id.nuevaSeccion)
        val nuevaSeccionBtn: Button = findViewById(R.id.agregarSeccion)
        val nuevatareabtn = findViewById<ImageView>(R.id.nuevatareabtn)
        var listView: ListView = findViewById(R.id.listView)
        val radioButtonLayout: RadioGroup = findViewById(R.id.radioButtonLayout)
        val spinnerSecciones: Spinner = findViewById(R.id.spSecciones)
        val secciones = DAO.mostrarSecciones(this, idUsuario)
        var itemList = DAO.mostrarTareas(this, idUsuario)

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, secciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSecciones.adapter = adapter2

        // Se toca para modificar
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedText = listView.getItemAtPosition(position).toString()
            val idTarea = DAO.getIDTarea(this,selectedText,idUsuario)
            val intent = Intent(this, Modify::class.java)
            intent.putExtra("IdUsuario", idUsuario)
            intent.putExtra("Tarea", idTarea)
            startActivity(intent)
        }

        val marginVertical = resources.getDimensionPixelSize(com.google.android.material.R.dimen.mtrl_card_checked_icon_margin) // Define el valor del margen vertical en dimens.xml

        //Según selección de spinner Secciones
        spinnerSecciones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //Actualizamos lista de tareas
                val seccionElegida = parent.getItemAtPosition(position).toString()
                itemList=DAO.mostrarTareasXSeccion(this@ToDoList,idUsuario,seccionElegida);
                adapter.clear()
                adapter.addAll(itemList)
                adapter.notifyDataSetChanged()
                listView.adapter = adapter
                radioButtonLayout.removeAllViews()

                //Crear checkbox
                for (item in itemList) {
                    val checkBox = CheckBox(this@ToDoList)
                    checkBox.id = View.generateViewId()

                    val layoutParams = RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(0, marginVertical, 0, marginVertical)

                    radioButtonLayout.addView(checkBox, layoutParams)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Listamos tareas
                itemList=DAO.mostrarTareas(this@ToDoList,idUsuario);
                adapter.clear()
                adapter.addAll(itemList)
                adapter.notifyDataSetChanged()
                listView.adapter = adapter
                radioButtonLayout.removeAllViews()
                //Creamos checkbox
                for (item in itemList) {
                    val checkBox = CheckBox(this@ToDoList)
                    checkBox.id = View.generateViewId()

                    val layoutParams = RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(0, marginVertical, 0, marginVertical)

                    radioButtonLayout.addView(checkBox, layoutParams)
                }
            }
        }

        // Generar los RadioButtons en función de los elementos de la lista



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
