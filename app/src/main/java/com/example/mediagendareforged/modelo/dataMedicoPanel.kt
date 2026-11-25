package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataMedicoPanel(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("especialidad") val especialidad: String = "",
    @SerializedName("consultorio") val consultorio: String = "",
    @SerializedName("ubicacion") val ubicacion: String = ""
)
