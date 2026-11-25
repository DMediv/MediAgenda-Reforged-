package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName


data class dataInfoPaciente(
    @SerializedName("paciente") val paciente: dataPacienteDetalle = dataPacienteDetalle(),
    @SerializedName("historial_medico") val historialMedico: dataHistorialMedico? = null
)
