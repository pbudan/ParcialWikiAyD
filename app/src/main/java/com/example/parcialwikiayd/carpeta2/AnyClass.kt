package com.example.parcialwikiayd.carpeta2

import android.content.Context
import android.util.Log
import ayds.observer.Subject
import com.example.parcialwikiayd.carpeta3.Pais
import com.example.parcialwikiayd.carpeta1.DataRepo
import kotlin.concurrent.thread

class AnyClass(context: Context) {

    val observer = Subject<Pair<String, Pais>>()
    private val repository = DataRepo(context)

    fun fetchBandera(paisSeleccionado: String) {
        thread {
            val result = repository.fetchBandera(paisSeleccionado)
            Log.e("RESULT", result.toString())
            //notifica sobre el pais seleccionado
            observer.notify(Pair(paisSeleccionado, result))
        }
    }
}