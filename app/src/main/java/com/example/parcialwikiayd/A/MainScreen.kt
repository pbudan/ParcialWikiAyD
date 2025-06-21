package com.example.parcialwikiayd.A

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parcialwikiayd.B.SomeClass
import com.example.parcialwikiayd.R

class MainScreen : AppCompatActivity() {
    private lateinit var dependency: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dependency = SomeClass(this)
        dependency.observer.subscribe { result ->
            onVuelo(result.first, result.second)
        }

        val spinner = findViewById<Spinner>(R.id.spinnerPaises)
        val paises = listOf("Argentina", "Chile", "Brasil", "Perú")

        //El dropdown en android se llama Spinner y se le debe poner un adapter con los valores a desplegar
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //Se le agrega un listener al spinner para reaccionar a los cambios de selección de items
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val pais = paises[position]
                dependency.fetchVuelos(pais)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    fun onVuelo(pais: String, vuelos: MutableList<String>) {
        val mensaje = "Vuelos sobre $pais"
        val mensajeVuelos = buildString {
            vuelos.forEachIndexed{index,vuelo ->
                append("${index + 1}. $vuelo\n")//es un solo string con saltos de linea
            }
        }

        //esto se ejecuta en la interfaz grafica
        runOnUiThread {
            findViewById<TextView>(R.id.textMensaje).text= mensaje
            findViewById<TextView>(R.id.textVuelo).text = mensajeVuelos
        }
    }
}