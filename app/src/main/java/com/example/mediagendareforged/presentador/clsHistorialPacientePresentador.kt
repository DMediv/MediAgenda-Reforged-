package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsMedicoModelo
import com.example.mediagendareforged.vista.HistorialPacienteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsHistorialPacientePresentador(private val vista: HistorialPacienteActivity) {

    companion object {
        const val CONMIGO = "conmigo"
        const val OTRAS = "otras"
    }

    private val modelo = clsMedicoModelo()
    private var ambitoPedido = ""

    fun cargarCitas(idPaciente: Int, ambito: String) {
        if (idPaciente == 0) {
            vista.mostrarErrorYCerrar("El paciente no existe.")
            return
        }

        ambitoPedido = ambito
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.obtenerHistorialCitasPaciente(idPaciente, ambito)

            withContext(Dispatchers.Main) {
                if (ambito != ambitoPedido) return@withContext

                vista.mostrarCargando(false)
                val citas = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && citas != null -> {
                        if (citas.isEmpty()) vista.mostrarVacio(resultado.message)
                        else vista.mostrarCitas(citas)
                    }
                    resultado.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarErrorYCerrar(resultado.message)
                }
            }
        }
    }
}