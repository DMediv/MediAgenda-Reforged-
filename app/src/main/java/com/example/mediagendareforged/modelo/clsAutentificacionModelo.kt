package com.example.mediagendareforged.modelo
import com.example.mediagendareforged.conexion.retrofitCliente

class clsAutentificacionModelo {

    suspend fun iniciarSesion(usuario: String, contrasena: String): dataRespuesta<dataSesion> {
        val resultado = retrofitCliente.llamar {
            retrofitCliente.api.iniciarSesion(dataLoginPeticion(usuario, contrasena))
        }

        if (resultado.status == retrofitCliente.EXITO && resultado.data != null) {
            retrofitCliente.token = resultado.data.token
        }
        return resultado
    }

    suspend fun verificarCorreo(correo: String): dataRespuesta<dataDisponibilidad> {
        return retrofitCliente.llamar {
            retrofitCliente.api.verificarDisponibilidad(correo = correo)
        }
    }

    suspend fun verificarUsuario(usuario: String): dataRespuesta<dataDisponibilidad> {
        return retrofitCliente.llamar {
            retrofitCliente.api.verificarDisponibilidad(usuario = usuario)
        }
    }

    suspend fun registrarPaciente(datos: dataRegistroPeticion): dataRespuesta<dataRegistro> {
        return retrofitCliente.llamar {
            retrofitCliente.api.registrarPaciente(datos)
        }
    }
}