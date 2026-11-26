package com.example.mediagendareforged.modelo

import com.example.mediagendareforged.conexion.retrofitCliente

class clsMedicoModelo {

    suspend fun obtenerPanel(): dataRespuesta<dataPanelMedico> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerPanelMedico()
        }
    }
}