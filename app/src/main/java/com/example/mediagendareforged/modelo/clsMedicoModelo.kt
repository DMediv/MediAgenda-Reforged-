package com.example.mediagendareforged.modelo

import com.example.mediagendareforged.conexion.retrofitCliente

class clsMedicoModelo {

    suspend fun obtenerPanel(): dataRespuesta<dataPanelMedico> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerPanelMedico()
        }
    }

    suspend fun obtenerDetalleCita(idCita: Int): dataRespuesta<dataCita> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerDetalleCita(idCita)
        }
    }

    suspend fun obtenerMisPacientes(): dataRespuesta<List<dataPacienteResumen>> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerMisPacientes()
        }
    }

    suspend fun obtenerInfoPaciente(idPaciente: Int): dataRespuesta<dataInfoPaciente> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerInfoPaciente(idPaciente)
        }
    }

    suspend fun obtenerHistorialCitasPaciente(idPaciente: Int, ambito: String): dataRespuesta<List<dataCita>> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerHistorialCitasPaciente(idPaciente, ambito)
        }
    }

    suspend fun obtenerDiasConCitas(mes: String): dataRespuesta<dataDiasMes> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerDiasConCitas(mes)
        }
    }

    suspend fun obtenerAgenda(fecha: String): dataRespuesta<List<dataCita>> {
        return retrofitCliente.llamar {
            retrofitCliente.api.obtenerAgenda(fecha)
        }
    }
}