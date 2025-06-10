package com.example.preparcialayd

import B.DataRepo
import B.PriceObserver
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainScreen : AppCompatActivity(), PriceObserver {
    private val repo = DataRepo(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        repo.addObserver(this)

        val spinner = findViewById<Spinner>(R.id.spinnerMonedas)
        val monedas = listOf("USD", "ARS", "CAD", "BTC")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val monedaSeleccionada = monedas[position]
                repo.fetchPrice(monedaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    override fun onPrice(symbol: String, price: Double) {
        val mensaje = "$symbol – $price"
        runOnUiThread {
        findViewById<TextView>(R.id.textPrecio).text = "$symbol – $price"
        }
    }
}