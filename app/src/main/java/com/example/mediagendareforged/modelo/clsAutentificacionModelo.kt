package com.example.mediagendareforged.modelo

import com.example.mediagendareforged.conexion.retrofitCliente

class clsAutentificacionModelo {
    suspend fun iniciarSesion(usuario: String, contrasena: String): dataRespuesta<dataSesion> {
        val resultado = retrofitCliente.llamar {
            retrofitCliente.api.iniciarSesion(dataLoginPeticion(usuario, contrasena))
        }

        if (resultado.status == "success" && resultado.data != null) {
            retrofitCliente.token = resultado.data.token
        }
        return resultado
    }
}