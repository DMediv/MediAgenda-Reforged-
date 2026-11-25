package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataResumenHoy(
    @SerializedName("programadas") val programadas: Int = 0,
    @SerializedName("atendidas") val atendidas: Int = 0
)
