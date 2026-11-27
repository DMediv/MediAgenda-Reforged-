package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsAutentificacionModelo
import com.example.mediagendareforged.modelo.clsPacienteModelo
import com.example.mediagendareforged.vista.PanelPacienteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsPanelPacientePresentador(private val vista: PanelPacienteActivity) {

    private val modeloPaciente = clsPacienteModelo()
    private val modeloAutentificacion = clsAutentificacionModelo()

    fun cargarPanel() {
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val perfil = modeloPaciente.obtenerPerfil()
            val citas = modeloPaciente.obtenerMisCitas("proximas")

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)

                when (perfil.status) {
                    retrofitCliente.SESION_EXPIRADA -> { vista.abrirSesionExpirada(); return@withContext }
                    retrofitCliente.SIN_CONEXION -> { vista.abrirSinConexion(); return@withContext }
                }

                val datosPerfil = perfil.data
                if (perfil.status == retrofitCliente.EXITO && datosPerfil != null) {
                    vista.mostrarSaludo(datosPerfil.nombres)
                }

                val lista = citas.data
                when {
                    citas.status == retrofitCliente.EXITO && lista != null -> {
                        val proxima = lista.firstOrNull()       // la más cercana
                        if (proxima == null) {
                            vista.mostrarSinCitas()
                        } else {
                            vista.mostrarProximaCita(
                                proxima.id,
                                clsFechas.mesCorto(proxima.fecha),
                                clsFechas.dia(proxima.fecha),
                                proxima.hora,
                                proxima.medico,
                                "${proxima.especialidad} · ${proxima.consultorio}",
                                proxima.estado
                            )
                        }
                    }
                    citas.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                    citas.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarAviso(citas.message)
                }
            }
        }
    }

    fun cerrarSesion() {
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            modeloAutentificacion.cerrarSesion()
            withContext(Dispatchers.Main) { vista.abrirLogin() }
        }
    }
}