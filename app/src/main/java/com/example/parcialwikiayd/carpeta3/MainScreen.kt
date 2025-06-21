package com.example.parcialwikiayd.carpeta3

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.parcialwikiayd.carpeta2.AnyClass
import com.example.parcialwikiayd.R

class MainScreen : AppCompatActivity() {
    private lateinit var dependency: AnyClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dependency = AnyClass(this)
        dependency.observer.subscribe { result ->
            onCountry(result.first, result.second)
        }

        val spinner = findViewById<Spinner>(R.id.spinnerPaises)
        val paises = listOf(
            "Argentina", "Brasil", "Chile", "Perú", "Colombia", "Uruguay",
            "Paraguay", "México", "España", "Italia", "Francia"
        )
        //El dropdown en android se llama Spinner y
        // se le debe poner un adapter con los valores a desplegar
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //Se le agrega un listener al spinner para reaccionar a los cambios de selección de items
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val pais = paises[position]
                dependency.fetchBandera(pais)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    fun onCountry(pais: String, detalle: Pais) {
        val descripcion = "Nombre común: ${detalle.nombreComun}\n" +
                "Nombre oficial: ${detalle.nombreOficial}\n\n" +
                "Descripción de la bandera:\n${detalle.descripcionBandera}"

        //esto se ejecuta en la interfaz grafica
        runOnUiThread {
            findViewById<TextView>(R.id.textDescripcion).text = descripcion
            Glide.with(this)
                .load(detalle.bandera)
                .into(findViewById(R.id.imagenBandera))
        }
    }
}