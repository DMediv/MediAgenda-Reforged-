package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataDiasMes(
    @SerializedName("mes") val mes: String = "",
    @SerializedName("dias") val dias: List<String> = emptyList()
)
