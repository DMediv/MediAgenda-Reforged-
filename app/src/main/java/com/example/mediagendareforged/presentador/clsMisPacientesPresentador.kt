package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsMedicoModelo
import com.example.mediagendareforged.modelo.dataPacienteResumen
import com.example.mediagendareforged.vista.MisPacientesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Normalizer

class clsMisPacientesPresentador(private val vista: MisPacientesActivity) {

    private val modelo = clsMedicoModelo()
    private var todos: List<dataPacienteResumen> = emptyList()
    private var busqueda = ""

    fun cargarPacientes() {
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.obtenerMisPacientes()

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)
                val lista = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && lista != null -> {
                        // La fecha se convierte una sola vez: "2026-11-09" → "lun 9 nov"
                        todos = lista.map { it.copy(ultimaCita = clsFechas.fechaCorta(it.ultimaCita)) }
                        filtrar(busqueda)
                    }
                    resultado.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarAviso(resultado.message)
                }
            }
        }
    }

    fun filtrar(texto: String) {
        busqueda = texto.trim()
        val buscado = normalizar(busqueda)
        val filtrados = todos.filter { normalizar(it.nombre).contains(buscado) }

        when {
            todos.isEmpty() -> vista.mostrarSinPacientes()
            filtrados.isEmpty() -> vista.mostrarSinResultados()
            else -> vista.mostrarPacientes(filtrados)
        }
    }
    private fun normalizar(texto: String): String {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace(Regex("\\p{M}"), "")
            .lowercase()
    }
}