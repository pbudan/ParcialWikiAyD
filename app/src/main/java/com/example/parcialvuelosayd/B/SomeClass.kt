package com.example.parcialvuelosayd.B

import android.content.Context
import android.util.Log
import ayds.observer.Subject
import com.example.parcialvuelosayd.C.DataRepo
import kotlin.concurrent.thread
import kotlin.math.roundToInt

class SomeClass(context: Context) {

    val observer = Subject<Pair<String, MutableList<String>>>()
    private val repository = DataRepo(context)

    fun fetchVuelos(paisSeleccionado: String) {
        thread {
            val result = repository.fetchVuelos(paisSeleccionado)
            Log.e("RESULT", result.toString())
            //notifica los vuelos que pasan por sobre el pais seleccionado
            observer.notify(Pair(paisSeleccionado, result))
        }
    }
}