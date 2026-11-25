package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataPacienteResumen(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("ultima_cita") val ultimaCita: String = ""
)
