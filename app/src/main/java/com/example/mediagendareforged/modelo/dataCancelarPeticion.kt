package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataCancelarPeticion(
    @SerializedName("id_cita") val idCita: Int = 0,
    @SerializedName("motivo") val motivo: String? = null
)
