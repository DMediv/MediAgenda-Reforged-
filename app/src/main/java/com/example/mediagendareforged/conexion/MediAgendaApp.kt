package com.example.mediagendareforged.conexion

import android.app.Application
import android.os.SystemClock
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.mediagendareforged.vista.clsNavegacion

class MediAgendaApp : Application() {

    private val MINUTOS_SEGUNDO_PLANO = 10
    private var momentoSegundoPlano = 0L

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onStop(owner: LifecycleOwner) {
                momentoSegundoPlano = SystemClock.elapsedRealtime()
            }

            override fun onStart(owner: LifecycleOwner) {
                if (momentoSegundoPlano == 0L || retrofitCliente.token.isEmpty()) return
                val minutos = (SystemClock.elapsedRealtime() - momentoSegundoPlano) / 60_000
                if (minutos >= MINUTOS_SEGUNDO_PLANO) {
                    retrofitCliente.token = ""
                    clsNavegacion.abrirSesionExpirada(this@MediAgendaApp)
                }
            }
        })
    }
}