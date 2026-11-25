package com.example.mediagendareforged.modelo
import com.google.gson.annotations.SerializedName

data class dataDisponibilidad(
    @SerializedName("disponible") val disponible: Boolean = false
)
