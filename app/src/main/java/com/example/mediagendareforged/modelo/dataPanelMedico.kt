package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataPanelMedico(
    @SerializedName("medico") val medico: dataMedicoPanel = dataMedicoPanel(),
    @SerializedName("resumen_hoy") val resumenHoy: dataResumenHoy = dataResumenHoy(),
    @SerializedName("siguiente_cita") val siguienteCita: dataCita? = null
)
