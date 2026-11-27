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

    fun abrirPanelMedico(activity: Activity) {
        val intent = Intent(activity, PanelMedicoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        activity.startActivity(intent)
    }

    fun abrirDestinoMedico(activity: Activity, destino: Class<*>) {
        val intentDestino = Intent(activity, destino)
        if (activity is PanelMedicoActivity) {
            activity.startActivity(intentDestino)
        } else {
            val intentPanel = Intent(activity, PanelMedicoActivity::class.java)
            intentPanel.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            activity.startActivities(arrayOf(intentPanel, intentDestino))
        }
    }

    fun abrirPanelPaciente(activity: Activity) {
        val intent = Intent(activity, PanelPacienteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        activity.startActivity(intent)
    }

    fun abrirDestinoPaciente(activity: Activity, destino: Class<*>) {
        val intentDestino = Intent(activity, destino)
        if (activity is PanelPacienteActivity) {
            activity.startActivity(intentDestino)
        } else {
            val intentPanel = Intent(activity, PanelPacienteActivity::class.java)
            intentPanel.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            activity.startActivities(arrayOf(intentPanel, intentDestino))
        }
    }
}