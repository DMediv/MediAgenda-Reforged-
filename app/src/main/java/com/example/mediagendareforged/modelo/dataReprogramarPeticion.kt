package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataReprogramarPeticion(
    @SerializedName("id_cita") val idCita: Int = 0,
    @SerializedName("fecha") val fecha: String = "",
    @SerializedName("hora") val hora: String = "",
    @SerializedName("motivo") val motivo: String? = null
)
