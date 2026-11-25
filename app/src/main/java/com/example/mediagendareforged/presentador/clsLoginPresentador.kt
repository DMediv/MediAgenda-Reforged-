package com.example.mediagendareforged.presentador

import android.util.Log
import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsAutentificacionModelo
import com.example.mediagendareforged.vista.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsLoginPresentador(private val vista: LoginActivity) {

    private val modelo = clsAutentificacionModelo()

    fun iniciarSesion(usuario: String, contrasena: String) {
        val usuarioLimpio = usuario.trim()
        vista.ocultarError()

        val errorUsuario = clsValidaciones.validarObligatorio(usuarioLimpio)
        val errorContrasena = clsValidaciones.validarObligatorio(contrasena)
        vista.mostrarErrorUsuario(errorUsuario)
        vista.mostrarErrorContrasena(errorContrasena)
        if (errorUsuario != null || errorContrasena != null) return

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.iniciarSesion(usuarioLimpio, contrasena)

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)
                val sesion = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && sesion != null -> {
                        Log.d("MediAgenda", "Sesión iniciada: rol=${sesion.rol}, nombre=${sesion.nombre}")
                        when (sesion.rol) {
                            "paciente" -> vista.abrirPanelPaciente(sesion.nombre)
                            "medico" -> vista.abrirPanelMedico(sesion.nombre)
                            else -> vista.mostrarError("Rol no reconocido. Intenta más tarde.")
                        }
                    }
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarError(resultado.message)
                }
            }
        }
    }


}