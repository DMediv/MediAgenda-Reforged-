package com.example.mediagendareforged.modelo

import com.example.mediagendareforged.conexion.retrofitCliente

class clsPacienteModelo {

    suspend fun obtenerPerfil(): dataRespuesta<dataPerfil> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerPerfil()
        }
    }

    suspend fun obtenerMisCitas(tipo: String, estado: String? = null): dataRespuesta<List<dataCita>> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerMisCitas(tipo, estado)
        }
    }
}