package com.example.mediagendareforged.vista

import android.app.Activity
import android.content.Context
import android.content.Intent


object clsNavegacion {

    fun abrirSinConexion(activity: Activity) {
        activity.startActivity(Intent(activity, SinConexionActivity::class.java))
    }

    fun abrirSesionExpirada(contexto: Context) {
        val intent = Intent(contexto, SesionExpiradaActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        contexto.startActivity(intent)
    }

    fun abrirLogin(contexto: Context) {
        val intent = Intent(contexto, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        contexto.startActivity(intent)
    }
}