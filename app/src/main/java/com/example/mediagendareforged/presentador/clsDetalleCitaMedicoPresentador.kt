package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsMedicoModelo
import com.example.mediagendareforged.vista.DetalleCitaMedicoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsDetalleCitaMedicoPresentador (private val vista: DetalleCitaMedicoActivity) {

    private val modelo = clsMedicoModelo()

    fun cargarDetalle(idCita: Int) {
        if (idCita == 0) {
            vista.mostrarErrorYCerrar("La cita no existe.")
            return
        }

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.obtenerDetalleCita(idCita)

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)
                val cita = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && cita != null -> {
                        vista.mostrarCita(
                            cita.idPaciente,
                            cita.paciente,
                            clsFechas.fechaLarga(cita.fecha),
                            clsFechas.rangoHora(cita.hora),
                            cita.consultorio,
                            cita.ubicacion,
                            cita.estado
                        )
                    }
                    resultado.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarErrorYCerrar(resultado.message)   // p. ej. 404
                }
            }
        }
    }
}