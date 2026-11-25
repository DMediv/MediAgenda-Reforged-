package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataAgendarPeticion(
    @SerializedName("id_medico") val idMedico: Int = 0,
    @SerializedName("fecha") val fecha: String = "",
    @SerializedName("hora") val hora: String = ""
)
