package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataHorariosDia(
    @SerializedName("fecha") val fecha: String = "",
    @SerializedName("horarios") val horarios: List<String> = emptyList()
)
