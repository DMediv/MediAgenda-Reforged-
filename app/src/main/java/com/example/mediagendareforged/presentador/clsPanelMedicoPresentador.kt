package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsAutentificacionModelo
import com.example.mediagendareforged.modelo.clsMedicoModelo
import com.example.mediagendareforged.vista.PanelMedicoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsPanelMedicoPresentador(private val vista: PanelMedicoActivity) {

    private val modeloMedico = clsMedicoModelo()
    private val modeloAutentificacion = clsAutentificacionModelo()

    fun cargarPanel() {
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modeloMedico.obtenerPanel()

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)
                val panel = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && panel != null -> {
                        val medico = panel.medico
                        vista.mostrarMedico(medico.nombre, "${medico.especialidad} · ${medico.consultorio}")
                        vista.mostrarResumen(panel.resumenHoy.programadas, panel.resumenHoy.atendidas)

                        val cita = panel.siguienteCita
                        if (cita == null) {
                            vista.mostrarSinCitas()
                        } else {
                            vista.mostrarSiguienteCita(
                                cita.id,
                                clsFechas.mesCorto(cita.fecha),
                                clsFechas.dia(cita.fecha),
                                cita.hora,
                                cita.paciente,
                                cita.consultorio,
                                cita.estado
                            )
                        }
                    }
                    resultado.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarAviso(resultado.message)
                }
            }
        }
    }

    fun cerrarSesion() {
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            modeloAutentificacion.cerrarSesion()
            withContext(Dispatchers.Main) {
                vista.abrirLogin()
            }
        }
    }
}